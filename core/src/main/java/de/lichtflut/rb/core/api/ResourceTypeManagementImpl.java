/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.query.QueryManager;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.schema.model.impl.ResourceTypeInstanceImpl;

/**
 * TODO: To comment
 * [INSERT DESCRIPTION HERE]
 * 
 * Created: May 18, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class ResourceTypeManagementImpl implements ResourceTypeManagement{

	private ArastrejuGate gate;
	
	// -- CONSTRUCTOR---------------------------------------
	
	/**
	 * 
	 */
	public ResourceTypeManagementImpl(ArastrejuGate gate){
		this.gate = gate;
	}
	
	// -----------------------------------------------------
	
	/**
	 * TODO: To comment
	 */
	public boolean createOrUpdateRTInstance(ResourceTypeInstance<Object> instance) {
		//Check if the resourceID is still null
		//Try to find an existing instance of this resource!?
		//Initialize modeling conversation
		ModelingConversation mc = gate.startConversation();
		instance.createAssociations(null);
		mc.attach(instance);
		return true;
	}

	// -----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(ResourceSchema schema, String filter) {
		if(filter.equals("")) return loadAllResourceTypeInstancesForSchema(schema);
		Collection<ResourceTypeInstance> output = new ArrayList<ResourceTypeInstance>();
		QueryManager qManager = gate.startConversation().createQueryManager();
		List<ResourceNode> nodes = qManager.findByTag(filter);
		for (ResourceNode resourceNode : nodes) {
			//Check if this Instance depends on a ResourceSchema
			Set<SemanticNode> clients = resourceNode.getAssociationClients(RDF.TYPE);
			for (SemanticNode semanticNode : clients) {
				if(semanticNode.isResourceNode() &&
				   semanticNode.asResource().getQualifiedName().equals(schema.getDescribedResourceID().getQualifiedName())){
					if(semanticNode.asResource().getSingleAssociationClient(RBSchema.DESCRIBED_BY) != null){
						output.add(new ResourceTypeInstanceImpl(schema,resourceNode));
						break;
					}
				}
			}
		}
		return output;
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(ResourceSchema schema) {
	      Collection<ResourceTypeInstance> output = new ArrayList<ResourceTypeInstance>();
          ModelingConversation mc = gate.startConversation();
          Set<Statement> statements = mc.createQueryManager().findIncomingStatements(schema.getDescribedResourceID());
          for (Statement statement : statements) {
             if(statement.getPredicate().equals(RDF.TYPE)){
                 ResourceTypeInstance  instance = new ResourceTypeInstanceImpl(schema, statement.getSubject().asResource());
                 output.add(instance);
             }
          }
         return output;
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(Collection<ResourceSchema> schemas) {
		Collection<ResourceTypeInstance> output = new ArrayList<ResourceTypeInstance>();
	    for (ResourceSchema resourceSchema : schemas) {
			output.addAll(loadAllResourceTypeInstancesForSchema(resourceSchema));
		}
	    return output;
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(Collection<ResourceSchema> schemas, String filter) {
		if(filter.equals("")) return loadAllResourceTypeInstancesForSchema(schemas);
		Collection<ResourceTypeInstance> output = new ArrayList<ResourceTypeInstance>();
		for (ResourceSchema schema : schemas) {
			output.addAll(loadAllResourceTypeInstancesForSchema(schema, filter));
		}
		return output;
	}
	
	// -----------------------------------------------------	
}