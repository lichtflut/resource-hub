/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.util.Collection;
import java.util.Set;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstanceImpl;

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
	
	public Collection<ResourceTypeInstance<Object>> loadAllResourceTypeInstancesForSchema(ResourceSchema schema) {
		ModelingConversation mc = gate.startConversation();
		Set<Statement> statements = mc.createQueryManager().findIncomingStatements(schema.getDescribedResourceID());
		for (Statement statement : statements) {
			if(statement.getPredicate().equals(RDF.TYPE));
			ResourceTypeInstanceImpl  instance = new ResourceTypeInstanceImpl(schema, statement.getSubject().asResource());
			//....
		}
		
		return null;
	}
	
	// -----------------------------------------------------	
}