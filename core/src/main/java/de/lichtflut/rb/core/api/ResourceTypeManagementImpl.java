/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.ModelingConversation;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;

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
}
