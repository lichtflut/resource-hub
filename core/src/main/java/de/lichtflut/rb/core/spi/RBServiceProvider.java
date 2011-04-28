/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi;

import org.arastreju.sge.ArastrejuGate;

/**
 * The ServiceProvider which provides all existing RB-Services
 * 
 * Created: Apr 28, 2011
 *
 * @author Nils Bleisch
 */
public interface RBServiceProvider {
	
	ArastrejuGate openArastejuGateInstance();
	ResourceSchemaManagement getResourceSchemaManagement();
	//TODO: Add more services
	/*IdentityManagement getIdentityManagment();
	MessagingService get MessagingService();
	MessagingService getMessagingService();
	AdminService getAdminService();	*/
}
