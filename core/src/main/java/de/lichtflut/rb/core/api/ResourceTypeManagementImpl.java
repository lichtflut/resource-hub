/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.Statement;

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
		Collection<ResourceTypeInstance> output = new ArrayList<ResourceTypeInstance>();
		ModelingConversation mc = gate.startConversation();
		Set<Statement> statements = mc.createQueryManager().findIncomingStatements(schema.getDescribedResourceID());
		for (Statement statement : statements) {
			if(statement.getPredicate().equals(RDF.TYPE)){
				ResourceTypeInstance  instance = new ResourceTypeInstanceImpl(schema, statement.getSubject().asResource());
				//check if this instance does match the filter and add it to the output collection		
				List<String> keywords = Arrays.asList(filter.toLowerCase().split(filter));
				Collection<String> attributes = instance.getAttributeNames();
				boolean exit=false;
				for (String attribute :  attributes ) {
					if(exit) break;
					for(Object value :instance.getValuesFor(attribute)){
						for(String keyword : keywords){
							if(value.toString().contains(keyword)){
								output.add(instance);
								exit=true;
								break;
							}
						}
					}
					if(exit) break;
				}
			}
		}
		return output;
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(ResourceSchema schema) {
		return  loadAllResourceTypeInstancesForSchema(schema,"");
	}
	
	// -----------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(Collection<ResourceSchema> schemas) {
		return loadAllResourceTypeInstancesForSchema(schemas,"");
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceTypeInstance> loadAllResourceTypeInstancesForSchema(Collection<ResourceSchema> schemas, String filter) {
		Collection<ResourceTypeInstance> output = new ArrayList<ResourceTypeInstance>();
		for (ResourceSchema schema : schemas) {
			output.addAll(loadAllResourceTypeInstancesForSchema(schema, filter));
		}
		return output;
	}
	
	// -----------------------------------------------------	
}