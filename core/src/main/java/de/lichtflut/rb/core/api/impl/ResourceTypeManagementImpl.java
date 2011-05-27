/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

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
import de.lichtflut.rb.core.api.ResourceTypeManagement;
import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.schema.model.impl.ResourceTypeInstanceImpl;

/**
 * Reference implementation of {@ResourceTypeManagement} for a lucence based neo4j backend
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
	 * This is the standard constructor
	 * @param gate the ArastrejuGate instance which is necessary to store, update and resolve ResourceTypeInstances
	*/
	public ResourceTypeManagementImpl(ArastrejuGate gate){
		this.gate = gate;
	}
	
	// -----------------------------------------------------
	
	public boolean createOrUpdateRTInstance(ResourceTypeInstance<Object> instance) {
		
		ModelingConversation mc = gate.startConversation();
		instance.createAssociations(null);
		try{
			mc.attach(instance);
		}catch(Exception any){
			return false;
		}
		return true;
	}

	// -----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(ResourceSchema schema, String filter, SearchContext ctx) {
		if(filter==null || filter.equals("")) return loadAllResourceTypeInstancesForSchema(schema);
		if(ctx==null) ctx = SearchContext.CONJUNCT_MULTIPLE_KEYWORDS;
		filter = getConvertedFilterBySearchContext(filter, ctx);
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
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(Collection<ResourceSchema> schemas, String filter, SearchContext ctx) {
		if(filter==null || filter.equals("")) return loadAllResourceTypeInstancesForSchema(schemas);
		//Check if context is still null
		if(ctx==null) ctx = SearchContext.CONJUNCT_MULTIPLE_KEYWORDS;
		Collection<ResourceTypeInstance> output = new ArrayList<ResourceTypeInstance>();
		for (ResourceSchema schema : schemas) {
			output.addAll(loadAllResourceTypeInstancesForSchema(schema, filter,ctx));
		}
		return output;
	}
	
	// --PRIVATE MEMBER---------------------------------------	
	
	
	/**
	 * Helper method to convert the filter in a way that the underlying lucene engine can work with.
	 */
	private String getConvertedFilterBySearchContext(String filter,SearchContext ctx) {
		switch(ctx){
			case CONJUNCT_MULTIPLE_KEYWORDS:{
				filter = filter.replace("*", " ");
				filter = filter.replace("?", " ");
				String[] keywords = filter.split(" ");
				StringBuilder convertedInput = new StringBuilder();
				for (int i = 0; i < keywords.length; i++) {
					if(keywords[i].length()>0){
						convertedInput.append(keywords[i]+"*" + ((i < (keywords.length-1)) ? "AND" : "") + " ");
					}else{
						convertedInput.append(keywords[i]);
					}
				}
				filter=  convertedInput.toString();	
				break;
			}
		}
		return filter;
	}
	
}