/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;

/**
 * <p>
 *  Interface for factory of a service provider.
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
	AuthModule createAuthModule();
	
	/**
	 * Create a security service instance for login.
	 * @return The security service.
	 */
	AuthenticationService createAuthenticationService();

}