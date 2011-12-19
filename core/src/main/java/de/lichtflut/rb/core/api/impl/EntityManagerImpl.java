package de.lichtflut.rb.core.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.ReferenceResolver;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.ServiceProvider;

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
public class EntityManagerImpl implements EntityManager, ReferenceResolver {

	private final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);
	
	private final ServiceProvider provider;
	
	// -----------------------------------------------------

	/**
	 * Constructor.
	 * @param provider The service provider.
	 */
	public EntityManagerImpl(final ServiceProvider provider) {
		this.provider = provider;
	}
	
	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBEntityImpl find(final ResourceID resourceID) {
		return find(resourceID, true);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<RBEntity> findByType(final ResourceID type) {
		final List<RBEntity> result = new ArrayList<RBEntity>();
		final ResourceSchema schema = provider.getSchemaManager().findSchemaForType(type);
		final List<ResourceNode> nodes = provider.getArastejuGate().createQueryManager().findByType(type);
		for (ResourceNode n : nodes) {
			n.getAssociations();
			RBEntityImpl entity = new RBEntityImpl(n, schema);
			result.add(resolveEntityReferences(entity, true));
		}
		return result;
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity create(final ResourceID type) {
		final ResourceSchema schema = provider.getSchemaManager().findSchemaForType(type);
		return new RBEntityImpl(schema);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
    public void store(final RBEntity entity) {
		final ModelingConversation mc = startConversation();
		final ResourceNode node = mc.resolve(entity.getID());
		SNOPS.associate(node, RDF.TYPE, entity.getType());
		SNOPS.associate(node, RDF.TYPE, RB.ENTITY);
		for (RBField field :entity.getAllFields()) {
			final Collection<SemanticNode> nodes = toSemanticNodes(field);
			SNOPS.replace(node, field.getPredicate(), nodes);
			if (field.isResourceReference()) {
				resolveEntityReferences(field, true);
			}
		}
		// Set label after all entity references have been resolved
		SNOPS.assure(node, RDFS.LABEL, new SNText(entity.getLabel()));
		mc.close();
    }
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void changeType(RBEntity entity, ResourceID type) {
		final ModelingConversation mc = startConversation();
		final ResourceNode node = mc.resolve(entity.getID());
		SNOPS.remove(node, RDF.TYPE, entity.getType());
		SNOPS.associate(node, RDF.TYPE, type);
		mc.close();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final ResourceID entityID) {
		final ModelingConversation mc = startConversation();
		final ResourceNode node = mc.resolve(entityID);
		mc.remove(node, false);
		mc.close();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void resolve(final RBEntityReference ref) {
		final RBEntityImpl resolved = find(ref, false);
		ref.setEntity(resolved);
	}
	
	// -----------------------------------------------------
	
	private ModelingConversation startConversation() {
		return provider.getArastejuGate().startConversation();
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	private RBEntityImpl find(final ResourceID resourceID, final boolean cascadeEager) {
		final ModelingConversation mc = provider.getArastejuGate().startConversation();
		final ResourceNode node = mc.findResource(resourceID.getQualifiedName());
		mc.close();
		if (node == null) {
			return null;
		}
		final ResourceID type = detectType(node);
		final RBEntityImpl entity;
		if (type == null) {
			entity = new RBEntityImpl(node, (ResourceID) null);
		} else {
			final ResourceSchema schema = provider.getSchemaManager().findSchemaForType(type.asResource());
			entity = new RBEntityImpl(node, schema);
		}
		resolveEntityReferences(entity, cascadeEager);
		return entity;
	}
	
	/**
	 * @param field The field to be translated.
	 * @return A collection of semantic nodes.
	 */
	private Collection<SemanticNode> toSemanticNodes(final RBField field) {
		final Collection<SemanticNode> result = new ArrayList<SemanticNode>();
		for (Object value : field.getValues()) {
			logger.info(field.getPredicate() + " : " + value);
			if (value == null) {
				// ignore
			} else if (field.isResourceReference()) {
				final ResourceID ref = (ResourceID) value;
				result.add(new SimpleResourceID(ref));
			} else {
				final ElementaryDataType datatype = Datatype.getCorrespondingArastrejuType(field.getDataType());
				result.add(new SNValue(datatype, value));
			}
		}
		return result;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param field
	 */
	private RBEntityImpl resolveEntityReferences(final RBEntityImpl entity, final boolean eager) {
		for (RBField field : entity.getAllFields()) {
			if (field.isResourceReference()) {
				resolveEntityReferences(field, eager);
			}
		}
		return entity;
	}
	
	/**
	 * @param field
	 */
	private void resolveEntityReferences(final RBField field, final boolean eager) {
		for (RBEntityReference value : field.<RBEntityReference>getValues()) {
			if (eager) {
				value.setEntity(find(value, false));
			} else {
				value.setResolver(this);
			}
		}
	}
	
	/**
	 * @param node the node.
	 * @return The detected type.
	 */
	private ResourceID detectType(ResourceNode node) {
		final Set<SemanticNode> objects = SNOPS.objects(node, RDF.TYPE);
		for (SemanticNode sn : objects) {
			if (RB.ENTITY.equals(sn)) {
				continue;
			} 
			return sn.asResource();
		}
		return null;
	}

}
