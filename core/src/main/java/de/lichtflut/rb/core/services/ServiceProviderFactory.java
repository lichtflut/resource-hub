/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;



/**
 * <p>
 *  Interface for fac
 * </p>
 *
 * <p>
 * 	Created Jan 10, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ServiceProviderFactory {

	/**
	 * Create the service provider.
	 * @return The service provider.
	 */
	ServiceProvider create();
	
	/**
	 * Create a security service instance for login.
	 * @return The security service.
	 */
	AuthenticationService getAuthenitcationService();

}