package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.common.DomainNamespacesHandler;
import de.lichtflut.rb.core.common.SchemaIdentifyingType;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.ValidationException;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBFieldValue;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.TypeManager;

/**
 * <p>
 * Implementation of {@link EntityManager}.
 * </p>
 * 
 * <p>
 * Created Oct 28, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class EntityManagerImpl implements EntityManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManagerImpl.class);

	private TypeManager typeManager;

	private SchemaManager schemaManager;

	private Conversation conversation;

	private DomainNamespacesHandler dnsHandler;

	// ---------------- Constructor -------------------------

	/**
	 * Default constructor.
	 */
	public EntityManagerImpl() {
	}

	/**
	 * Constructor.
.	 * @param typeManager
	 * @param schemaManager
	 * @param conversation
	 */
	public EntityManagerImpl(final TypeManager typeManager, final SchemaManager schemaManager, final Conversation conversation) {
		this.typeManager = typeManager;
		this.schemaManager = schemaManager;
		this.conversation = conversation;
	}

	// -----------------------------------------------------

	/**
	 * @return An entity, or <code>null</code> if none found
	 */
	@Override
	public RBEntityImpl find(final ResourceID resourceID) {
		return find(resourceID, true);
	}

	/**
	 * The creation of an Entity involves the following Steps:
	 * <ol>
	 *	<li>Get {@link SchemaIdentifyingType}</li>
	 *	<li>Prepare an Entity:
	 *	<ol>
	 *		<li>Check if a prototype is associated diretly</li>
	 *		<li>If no prototype found check the schema type for a prototype</li>
	 *		<li>If prototype found copy its values and set the appropriate RDF.TYPEs. Otherwise do nothing</li>
	 *	</ol>
	 *	</li>
	 *	<li>Create a {@link RBEntity}. If it has a schema associated, use it as parameter, otherwise use given type</li>
	 * </ol>
	 */
	@Override
	public RBEntity create(final ResourceID type) {
		ResourceNode typeNode = conversation.findResource(type.getQualifiedName());
		ResourceSchema schema = getSchemaFor(typeNode);

		ResourceNode entityNode = prepareEntityNode(typeNode, schema);
		entityNode.addAssociation(RDF.TYPE, type);
		if (schema != null) {
			return new RBEntityImpl(entityNode, schema).markTransient();
		} else {
			return new RBEntityImpl(entityNode, type).markTransient();
		}
	}


	@Override
	public void store(final RBEntity entity) {
		final ResourceNode node = entity.getNode();
		SNOPS.associate(node, RDF.TYPE, RBSystem.ENTITY);
		if (entity.getType() == null) {
			throw new IllegalStateException("Entity has no type, will not save it.");
		}
		for (RBField field : entity.getAllFields()) {
			final Collection<SemanticNode> nodes = toSemanticNodes(field);
			SNOPS.assure(node, field.getPredicate(), nodes);
			if (field.getVisualizationInfo().isEmbedded()) {
				storeEmbeddeds(field);
			} else if (field.isResourceReference()) {
				resolveEntityReferences(field);
			}
		}
		// Set label after all entity references have been resolved
		SNOPS.assure(node, RDFS.LABEL, new SNText(entity.getLabel()));
		conversation.attach(node);
	}

	/**
	 * Validates a {@link RBEntity} against {@link ResourceSchema} rules.
	 * 
	 * @param entity Entity to be validated
	 */
	public Map<Integer, List<RBField>> validate(final RBEntity entity) {
		Map<Integer, List<RBField>> errors = new HashMap<Integer, List<RBField>>();
		if (!entity.hasSchema()) {
			return errors;
		}
		for (RBField field : entity.getAllFields()) {
			try {
				validateRBField(field);
			} catch (ValidationException e) {
				append(errors, e, field);
			}
		}
		return errors;
	}

	@Override
	public void changeType(final RBEntity entity, final ResourceID type) {
		final ResourceNode node = conversation.resolve(entity.getID());
		SNOPS.remove(node, RDF.TYPE, entity.getType());
		SNOPS.remove(node, RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, entity.getType());
		SNOPS.associate(node, RDF.TYPE, type);
		SNOPS.associate(node, RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE, type);
	}

	@Override
	public void delete(final ResourceID entityID) {
		final ResourceNode node = conversation.resolve(entityID);
		conversation.remove(node);
	}

	// -- INJECTED DEPENDENCIES ---------------------------

	public void setTypeManager(final TypeManager typeManager) {
		this.typeManager = typeManager;
	}

	public void setSchemaManager(final SchemaManager schemaManager) {
		this.schemaManager = schemaManager;
	}

	public void setConversation(final Conversation conversation) {
		this.conversation = conversation;
	}

	public void setDnsHandler(final DomainNamespacesHandler dnsHandler) {
		this.dnsHandler = dnsHandler;
	}

	// ----------------------------------------------------

	private RBEntityImpl find(final ResourceID resourceID, final boolean resolveEmbeddeds) {
		if(null == resourceID){
			return null;
		}
		final ResourceNode node = conversation.findResource(resourceID.getQualifiedName());
		if (node == null) {
			return null;
		}
		final ResourceID type = typeManager.getTypeOfResource(node);
		final RBEntityImpl entity;
		if (type == null) {
			LOGGER.warn("RBEntity has no type: " + node);
			entity = new RBEntityImpl(node);
		} else {
			final ResourceSchema schema = schemaManager.findSchemaForType(type.asResource());
			if (schema != null) {
				entity = new RBEntityImpl(node, schema);
			} else {
				entity = new RBEntityImpl(node, type);
			}
		}
		resolveEntityReferences(entity, resolveEmbeddeds);
		return entity;
	}

	/**
	 * @param field The field to be translated.
	 * @return A collection of semantic nodes.
	 */
	private Collection<SemanticNode> toSemanticNodes(final RBField field) {
		final Collection<SemanticNode> result = new ArrayList<SemanticNode>();
		for (RBFieldValue fieldValue : field.getValues()) {
			Object value = fieldValue.getValue();
			LOGGER.info(field.getPredicate() + " : " + value);
			if (value == null) {
				// ignore
			} else if (field.getVisualizationInfo().isEmbedded() && value instanceof RBEntity) {
				final RBEntity ref = (RBEntity) value;
				result.add(ref.getID());
			} else if (field.isResourceReference()) {
				final ResourceID ref = (ResourceID) value;
				result.add(new SimpleResourceID(ref.getQualifiedName()));
			} else {
				final ElementaryDataType datatype = Datatype.getCorrespondingArastrejuType(field.getDataType());
				result.add(new SNValue(datatype, value));
			}
		}
		return result;
	}

	// ----------------------------------------------------

	private RBEntityImpl resolveEntityReferences(final RBEntityImpl entity, final boolean resolveEmbeddeds) {
		for (RBField field : entity.getAllFields()) {
			if (resolveEmbeddeds && field.getVisualizationInfo().isEmbedded()) {
				resolveEmbeddedEntityReferences(field);
			} else if (field.isResourceReference()) {
				resolveEntityReferences(field);
			}
		}
		return entity;
	}

	private void resolveEntityReferences(final RBField field) {
		for (RBFieldValue fieldValue : field.getValues()) {
			ResourceID id = (ResourceID) fieldValue.getValue();
			if (id != null) {
				fieldValue.setValue(conversation.resolve(id));
			}
		}
	}

	private void resolveEmbeddedEntityReferences(final RBField field) {
		for (RBFieldValue fieldValue : field.getValues()) {
			Object value = fieldValue.getValue();
			if (value instanceof RBEntity) {
				// everything O.K.
			} else if (value instanceof ResourceID) {
				ResourceID id = (ResourceID) value;
				fieldValue.setValue(find(id, false));
			}
		}
	}

	private void storeEmbeddeds(final RBField field) {
		for (RBFieldValue fieldValue : field.getValues()) {
			Object value = fieldValue.getValue();
			if (value instanceof RBEntity) {
				RBEntity entity = (RBEntity) value;
				store(entity);
			}
		}
	}

	private SNResource newEntityNode() {
		if (dnsHandler != null) {
			final QualifiedName qn = new QualifiedName(dnsHandler.getEntitiesNamespace(), UUID.randomUUID().toString());
			return new SNResource(qn);
		} else {
			return new SNResource();
		}
	}

	/**
	 * Validates a single {@link RBField} for consistency.
	 * @param field Field to be validated
	 * @throws ValidationException
	 */
	private void validateRBField(final RBField field) throws ValidationException {
		Cardinality cardinality = field.getCardinality();
		if(cardinality.getMinOccurs() > field.getValues().size() ||
				cardinality.getMaxOccurs() < field.getValues().size()){
			throw new ValidationException(ErrorCodes.CARDINALITY_EXCEPTION, "Cardinality does not match given values");
		}
	}

	/**
	 * Appends a given Map by a given ValidationException and a given RBField.
	 * @param errors Map
	 * @param exception Exception to be added
	 * @param field RBfield to be addd
	 */
	private void append(final Map<Integer, List<RBField>> errors, final ValidationException exception, final RBField field) {
		int errorCode = exception.getErrorCode();
		if(errors.containsKey(errorCode)){
			errors.get(errorCode).add(field);
		}else{
			List<RBField> fields = new ArrayList<RBField>();
			fields.add(field);
			errors.put(exception.getErrorCode(), fields);
		}
	}

	private ResourceSchema getSchemaFor(final ResourceNode type){
		ResourceSchema schema = schemaManager.findSchemaForType(type);
		if(null == schema){
			SNClass schemaIdentifier = SchemaIdentifyingType.of(type);
			schema = schemaManager.findSchemaForType(schemaIdentifier);
		}
		return schema;
	}

	private ResourceNode prepareEntityNode(final ResourceID type, final ResourceSchema schema) {
		ResourceNode entityNode = newEntityNode();
		ResourceNode prototype = getPrototype(type);
		if(null == prototype && null != schema){
			prototype = getPrototype(schema.getDescribedType());
		}
		if(null != prototype){
			LOGGER.debug("Found Prototype for {}.", type);
			if(isEntity(prototype)){
				entityNode = copy(prototype);
				if(null !=schema){
					// Set original type
					SNOPS.remove(entityNode, RDF.TYPE);
					entityNode.addAssociation(RDF.TYPE, RBSystem.ENTITY);
					entityNode.addAssociation(RDF.TYPE, type);
				}
				conversation.attach(entityNode);
			}else{
				LOGGER.debug("Prototype for {} is not of type RBSystem.ENTITY. Prototyping skipped.", type);
			}
		}
		return entityNode;
	}


	private ResourceNode copy(final SemanticNode prototype) {
		ResourceNode node = newEntityNode();
		for (Statement stmt: prototype.asResource().getAssociations()) {
			node.addAssociation(stmt.getPredicate(), stmt.getObject());
		}
		return node;
	}

	private boolean isEntity(final ResourceNode prototype) {
		boolean isEntity = false;
		for (Statement stmt : prototype.getAssociations()) {
			if(stmt.getObject().equals(RBSystem.ENTITY)){
				isEntity = true;
				break;
			}
		}
		return isEntity;
	}

	private ResourceNode getPrototype(final ResourceID classId) {
		ResourceNode classNode = conversation.findResource(classId.getQualifiedName());
		SemanticNode node = SNOPS.fetchObject(classNode, RBSystem.HAS_PROTOTYPE);
		if(null != node){
			return node.asResource();
		}
		return null;
	}

}
