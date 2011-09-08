package de.lichtflut.rb.core.api.impl;

import java.util.HashSet;
import java.util.List;

import org.arastreju.sge.ArastrejuGate;
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
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.model.nodes.views.SNEntity;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.INewRBEntityManagement;
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.NewRBEntity;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProvider;

/**
 * 
 * @author Raphael Esterle
 * 
 */

public class NewRBEntityManagement implements INewRBEntityManagement {

	private final INewRBServiceProvider provider;

	/**
	 * <p>
	 * This is the standard constructor.
	 * </p>
	 * 
	 * @param gate
	 *            the ArastrejuGate instance which is necessary to store, update
	 *            and resolve ResourceTypeInstances
	 */
	public NewRBEntityManagement(final INewRBServiceProvider provider) {
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
		return new NewRBEntity(node);
	}

	public List<ResourceNode> findByType(ResourceID type) {
		ModelingConversation mc = provider.getArastejuGateInstance()
				.startConversation();
		return mc.createQueryManager().findByType(type);
	}

	@Override
    public IRBEntity store(final IRBEntity entity) {

        ModelingConversation mc = provider.getArastejuGateInstance().startConversation();

        SNEntity newNode = new SNEntity(entity.getNode());
        
        ResourceNode sNode = mc.findResource(entity.getQualifiedName());
       
        if(null==sNode){
        	sNode = newNode;
        	sNode.addToAssociations(Association.create(sNode, RDF.TYPE, entity.getType(), null));
        }
        else{
        	for (IRBField field : entity.getAllFields()) {
        		ResourceID resourceID = new SimpleResourceID(field.getFieldName());
        		// Remove old Associations
        		for (Association assoc : sNode.getAssociations(resourceID)) {
					sNode.remove(assoc);
				};
				// Create new Associations
				for (Object val : field.getFieldValues()) {
					SemanticNode node = new SNValue(field.getDataType(), val);
					if(field.getDataType()==ElementaryDataType.RESOURCE){
						try{
						IRBEntity tempEntity = (IRBEntity) val;
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

        mc.attach(sNode);

        return new NewRBEntity(sNode);
    }

	@Override
	public void delete(final IRBEntity entity) {
		NewRBEntity sEntity = find(entity.getID());
		for (IRBField field : entity.getAllFields()) {
			ResourceID resourceID = new SimpleResourceID(field.getFieldName());
			for (Association assoc : sEntity.getNode().getAssociations(
					new SimpleResourceID(field.getFieldName()))) {
				sEntity.getNode().remove(assoc);
			}
		}
	}

	@Override
	public List<IRBEntity> findAllByType(final ResourceID type) {
		ModelingConversation mc = provider.getArastejuGateInstance()
				.startConversation();
		List<ResourceNode> nodes = mc.createQueryManager().findByType(type);
		System.out.println("-->" + nodes.size());
		for (ResourceNode n : nodes) {
			System.out.println("***" + n);
		}
		return null;
	}

}
