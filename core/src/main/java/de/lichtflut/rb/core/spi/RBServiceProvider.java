/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi;

import org.arastreju.sge.ArastrejuGate;

import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.api.ResourceTypeManagement;

/**
 * <p>
 * The ServiceProvider which provides all existing RB-Services
 * </p>
 * 
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 */
public interface RBServiceProvider {
	
	/**
	 * @return an instance of {@link ArastrejuGate} which depends on the specific ServiceProvider
	 */
	ArastrejuGate openArastejuGateInstance();
	
	// -----------------------------------------------------
	
	/**
	 * {@link ResourceSchemaManagement} provides the ability to generate, manipulate, maintain,
	 * persist and store an ResourceSchema through several I/O sources.
	 * It's also interpreting the schema, checks for consistency and contains powerful error-processing mechanisms. 
	 */
	ResourceSchemaManagement getResourceSchemaManagement();
	
	// -----------------------------------------------------
	
	/**
	 * {@link ResourceSchemaManagement} provides the ability to manage,
	 * persist and store Resource-Types and RT-Instances.
	 */
	ResourceTypeManagement getResourceManagement();
	
	// -----------------------------------------------------
	
	
	
	//TODO: Add more services
	/*IdentityManagement getIdentityManagment();
	MessagingService get MessagingService();
	MessagingService getMessagingService();
	AdminService getAdminService();	*/
}
