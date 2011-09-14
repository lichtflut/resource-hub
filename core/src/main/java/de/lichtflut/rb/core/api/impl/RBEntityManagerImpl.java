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
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.NewRBEntity;
import de.lichtflut.rb.core.spi.IRBServiceProvider;

/**
 *
 * @author Raphael Esterle
 *
 */
public class RBEntityManagerImpl implements EntityManager {

	private final IRBServiceProvider provider;

	/**
	 * <p>
	 * This is the standard constructor.
	 * </p>
	 *
	 * @param provider -
	 */
	public RBEntityManagerImpl(final IRBServiceProvider provider) {
		this.provider = provider;
	}

	@Override
	public NewRBEntity find(final ResourceID resourceID) {
		return find(resourceID, null);
	}

	/**
	 *
	 * @param resourceID
	 *            /
	 * @param schema
	 *            /
	 * @return /
	 */
	public NewRBEntity find(final ResourceID resourceID,
			final ResourceSchema schema) {
		ModelingConversation mc = provider.getArastejuGateInstance()
				.startConversation();
		ResourceNode node = mc.findResource(resourceID.getQualifiedName());
		mc.close();
		return new NewRBEntity(node, node.asEntity().getMainClass());
	}

	/**
	 *
	 * @param type -
	 * @return -
	 */
	public List<ResourceNode> findByType(final ResourceID type) {
		ModelingConversation mc = provider.getArastejuGateInstance()
				.startConversation();
		return mc.createQueryManager().findByType(type);
	}

	@Override
    public void store(final IRBEntity entity) {

        ModelingConversation mc = provider.getArastejuGateInstance().startConversation();

        ResourceNode newNode = entity.getNode();

        ResourceNode sNode = mc.findResource(entity.getQualifiedName());

        if(null==sNode){
        	System.out.println(entity);
        	Association.create(newNode, RDF.TYPE, entity.getType());
        	sNode = newNode;
        }else{
        	for (IRBField field : entity.getAllFields()) {
        		ResourceID resourceID = new SimpleResourceID(field.getFieldName());
        		// Remove old Associations
        		for (Association assoc : sNode.getAssociations(resourceID)) {
					sNode.remove(assoc);
				}
				// Create new Associations
				for (Object val : field.getFieldValues()) {
					SemanticNode node = new SNValue(field.getDataType(), val);
					if(field.getDataType()==ElementaryDataType.RESOURCE){
						try{
							IRBEntity tempEntity = (IRBEntity) val;
							store(tempEntity);
							node = new SNResource(tempEntity.getQualifiedName());
						}catch(ClassCastException e){
							System.out.println(val+" is not a ressource!");
							continue;
						}
					}
					SNOPS.replace(sNode, resourceID, node, null);
				}
            }
        }

    	System.out.println(newNode.getQualifiedName()+"*~"+sNode.getQualifiedName());
        mc.attach(sNode);
    }

	@Override
	public void delete(final IRBEntity entity) {
		NewRBEntity sEntity = find(entity.getID());
		for (IRBField field : entity.getAllFields()) {
			ResourceID resourceID = new SimpleResourceID(field.getFieldName());
			for (Association assoc : sEntity.getNode().getAssociations(
					new SimpleResourceID(field.getFieldName()))) {
				sEntity.getNode().remove(assoc);
				System.out.println("del-"+assoc);
			}
		}
	}

	@Override
	public List<IRBEntity> findAllByType(final ResourceID type) {
		ArrayList entities = new ArrayList<IRBEntity>();
		ModelingConversation mc = provider.getArastejuGateInstance()
				.startConversation();
		List<ResourceNode> nodes = mc.createQueryManager().findByType(type);
		for (ResourceNode n : nodes) {
			entities.add(new NewRBEntity(n, type));
		}

		return entities;
	}

	@Override
	public List<ResourceID> findAllTypes() {
		return null;
	}

}
