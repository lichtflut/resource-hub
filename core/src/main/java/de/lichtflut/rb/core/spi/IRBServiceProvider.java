/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.spi;

import org.arastreju.sge.ArastrejuGate;

import de.lichtflut.rb.core.api.ISchemaManagement;
import de.lichtflut.rb.core.api.RBEntityManager;

/**
  * <p>
 * The ServiceProvider which provides all existing RB-Services.
 * </p>
 *
 * Created: Aug 29, 2011
 *
 * @author Ravi Knox
 */
public interface IRBServiceProvider {
	/**
	 * @return an instance of {@link ArastrejuGate} which depends on the specific ServiceProvider
	 */
	ArastrejuGate getArastejuGateInstance();

	// -----------------------------------------------------

	/**
	 * {@link ISchemaManagement} provides the ability to generate, manipulate, maintain,
	 * persist and store an ResourceSchema through several I/O sources.
	 * It's also interpreting the schema, checks for consistency and contains powerful error-processing mechanisms.
	 * @return {@link ISchemaManagement}
	 */
	ISchemaManagement getResourceSchemaManagement();

	// -----------------------------------------------------

	/**
	 * {@link RBEntityManager} provides the ability to manage,
	 * persist and store RB-Entities.
	 * @return {@link RBEntityManager}
	 */
	RBEntityManager getRBEntityManagement();

	// -----------------------------------------------------

	//TODO: Add more services
	/*IdentityManagement getIdentityManagment();
	MessagingService get MessagingService();
	MessagingService getMessagingService();
	AdminService getAdminService();	*/
}
