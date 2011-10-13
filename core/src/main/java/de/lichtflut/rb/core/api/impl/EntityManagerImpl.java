package de.lichtflut.rb.core.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 *
 * @author Raphael Esterle
 */
public class EntityManagerImpl implements EntityManager {

	private final ServiceProvider provider;
	
	// -----------------------------------------------------

	/**
	 * <p>
	 * This is the standard constructor.
	 * </p>
	 *
	 * @param provider -
	 */
	public EntityManagerImpl(final ServiceProvider provider) {
		this.provider = provider;
	}
	
	// -----------------------------------------------------

	@Override
	public RBEntityImpl find(final ResourceID resourceID) {
		ModelingConversation mc = provider.getArastejuGate().startConversation();
		ResourceNode node = mc.findResource(resourceID.getQualifiedName());
		mc.close();
		return new RBEntityImpl(node, node.asEntity().getMainClass());
	}

	/**
	 *
	 * @param type -
	 * @return -
	 */
	public List<ResourceNode> findByType(final ResourceID type) {
		ModelingConversation mc = provider.getArastejuGate()
				.startConversation();
		return mc.createQueryManager().findByType(type);
	}

	@Override
    public void store(final RBEntity entity) {

        ModelingConversation mc = provider.getArastejuGate().startConversation();

        ResourceNode newNode = entity.getNode();

        ResourceNode sNode = mc.findResource(entity.getQualifiedName());

        if(null==sNode){
        	System.out.println(entity);
        	Association.create(newNode, RDF.TYPE, entity.getType());
        	sNode = newNode;
        }else{
        	for (RBField field : entity.getAllFields()) {
        		final ResourceID predicate = new SimpleResourceID(field.getPredicate());
        		// Remove old Associations
        		for (Association assoc : sNode.getAssociations(predicate)) {
					sNode.remove(assoc);
				}
				// Create new Associations
				for (Object val : field.getValues()) {
					SemanticNode node = new SNValue(field.getDataType(), val);
					if(field.getDataType()==ElementaryDataType.RESOURCE){
						try{
							RBEntity tempEntity = (RBEntity) val;
							store(tempEntity);
							node = new SNResource(tempEntity.getQualifiedName());
						}catch(ClassCastException e){
							System.out.println(val+" is not a ressource!");
							continue;
						}
					}
					SNOPS.replace(sNode, predicate, node);
				}
            }
        }

    	System.out.println(newNode.getQualifiedName()+"*~"+sNode.getQualifiedName());
        mc.attach(sNode);
    }

	@Override
	public void delete(final RBEntity entity) {
		RBEntityImpl sEntity = find(entity.getID());
		for (RBField field : entity.getAllFields()) {
			final ResourceID predicate = new SimpleResourceID(field.getPredicate());
			for (Association assoc : sEntity.getNode().getAssociations(predicate)) {
				sEntity.getNode().remove(assoc);
				System.out.println("del-"+assoc);
			}
		}
	}

	@Override
	public List<RBEntity> findAllByType(final ResourceID type) {
		List<RBEntity> result = new ArrayList<RBEntity>();
		ModelingConversation mc = provider.getArastejuGate().startConversation();
		List<ResourceNode> nodes = mc.createQueryManager().findByType(type);
		for (ResourceNode n : nodes) {
			result.add(new RBEntityImpl(n, type));
		}

		return result;
	}


}
