package de.lichtflut.rb.core.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
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
public class EntityManagerImpl implements EntityManager {

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
		final ModelingConversation mc = provider.getArastejuGate().startConversation();
		final ResourceNode node = mc.findResource(resourceID.getQualifiedName());
		mc.close();
		if (node == null) {
			return null;
		}
		final ResourceID type = node.asEntity().getMainClass();
		final ResourceSchema schema = provider.getSchemaManager().findSchemaForType(type);
		return new RBEntityImpl(node, schema);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<RBEntity> findByType(final ResourceID type) {
		final List<RBEntity> result = new ArrayList<RBEntity>();
		final ModelingConversation mc = provider.getArastejuGate().startConversation();
		final ResourceSchema schema = provider.getSchemaManager().findSchemaForType(type);
		final List<ResourceNode> nodes = mc.createQueryManager().findByType(type);
		for (ResourceNode n : nodes) {
			result.add(new RBEntityImpl(n, schema));
		}
		mc.close();
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
		SNOPS.replace(node, RDF.TYPE, entity.getType());
		for (RBField field :entity.getAllFields()) {
			final Collection<SemanticNode> nodes = toSemanticNodes(field);
			SNOPS.replace(node, field.getPredicate(), nodes);
		}
		mc.close();
    }

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final RBEntity entity) {
		throw new NotYetImplementedException();
	}
	
	// -----------------------------------------------------
	
	private ModelingConversation startConversation() {
		return provider.getArastejuGate().startConversation();
	}
	
	// -----------------------------------------------------
	
	/**
	 * @param field The field to be translated.
	 * @return A collection of semantic nodes.
	 */
	private Collection<SemanticNode> toSemanticNodes(final RBField field) {
		final Collection<SemanticNode> result = new ArrayList<SemanticNode>();
		for (Object value : field.getValues()) {
			if (value == null) {
				// ignore
			} else if (field.isResourceReference()) {
				final RBEntity ref = (RBEntity) value;
				result.add(ref.getID());
			} else {
				result.add(new SNValue(field.getDataType(), value));
			}
		}
		return result;
	}

}
