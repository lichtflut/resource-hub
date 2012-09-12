package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.common.DomainNamespacesHandler;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.TypeManager;

/**
 * <p>
 *  Implementation of {@link EntityManager}.
 * </p>
 *
 * <p>
 * 	Created Oct 28, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityManagerImpl implements EntityManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManagerImpl.class);

	private TypeManager typeManager;

	private SchemaManager schemaManager;

	private ModelingConversation conversation;

	private DomainNamespacesHandler dnsHandler;

	// -----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public EntityManagerImpl() { }

	public EntityManagerImpl(final TypeManager typeManager, final SchemaManager schemaManager, final ModelingConversation conversation) {
		this.typeManager = typeManager;
		this.schemaManager = schemaManager;
		this.conversation = conversation;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBEntityImpl find(final ResourceID resourceID) {
		return find(resourceID, true);
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity create(final ResourceID type) {
		final ResourceSchema schema = schemaManager.findSchemaForType(type);
		if (schema != null) {
			return new RBEntityImpl(newEntityNode(), schema);
		} else {
			return new RBEntityImpl(newEntityNode(), type);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void store(final RBEntity entity) {
		final ResourceNode node = entity.getNode();
		SNOPS.associate(node, RDF.TYPE, entity.getType());
		SNOPS.associate(node, RDF.TYPE, RBSystem.ENTITY);
		for (RBField field :entity.getAllFields()) {
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
	 * {@inheritDoc}
	 */
	@Override
	public void changeType(final RBEntity entity, final ResourceID type) {
		final ModelingConversation mc = conversation;
		final ResourceNode node = mc.resolve(entity.getID());
		SNOPS.remove(node, RDF.TYPE, entity.getType());
		SNOPS.associate(node, RDF.TYPE, type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final ResourceID entityID) {
		final ModelingConversation mc = conversation;
		final ResourceNode node = mc.resolve(entityID);
		mc.remove(node);
	}

	// -- DEPENDENCIES ------------------------------------

	public void setTypeManager(final TypeManager typeManager) {
		this.typeManager = typeManager;
	}

	public void setSchemaManager(final SchemaManager schemaManager) {
		this.schemaManager = schemaManager;
	}

	public void setConversation(final ModelingConversation conversation) {
		this.conversation = conversation;
	}

	public void setDnsHandler(final DomainNamespacesHandler dnsHandler) {
		this.dnsHandler = dnsHandler;
	}

	// ----------------------------------------------------

	private RBEntityImpl find(final ResourceID resourceID, final boolean resolveEmbeddeds) {
		final ResourceNode node = conversation.findResource(resourceID.getQualifiedName());
		if (node == null) {
			return null;
		}
		final ResourceID type = typeManager.getTypeOfResource(node);
		final RBEntityImpl entity;
		if (type == null) {
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
		for (Object value : field.getValues()) {
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
		for (int i=0; i < field.getSlots(); i++) {
			ResourceID id = (ResourceID) field.getValue(i);
			if (id != null) {
				field.setValue(i, conversation.resolve(id));
			}
		}
	}

	private void resolveEmbeddedEntityReferences(final RBField field) {
		for (int i=0; i < field.getSlots(); i++) {
			final Object value = field.getValue(i);
			if (value instanceof RBEntity) {
				// everything O.K.
			} else if (value instanceof ResourceID) {
				ResourceID id = (ResourceID) value;
				field.setValue(i, find(id, false));
			}
		}
	}


	private void storeEmbeddeds(final RBField field) {
		for (Object value : field.getValues()) {
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

}
