/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

/**
 * <p>
 *  Base interface for the pluggable auth module of an RB application.
 *  This module givea access to 
 *  <ul>
 *  	<li>The authentication service</li>
 *  	<li>The user management service</li>
 *  	<li>The domain management service</li>
 *  </ul>
 * </p>
 *
 * <p>
 * 	Created May 11, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface AuthModule {
	
	/**
	 * Get the service for authentication of users.
	 * @return The authentication service.
	 */
	AuthenticationService getAuthenticationService();
	
	/**
	 * Get the service for management of users.
	 * @return The user management service.
	 */
	UserManager getUserManagement();

	/**
	 * Get the service for management of domains and their roles and permissions.
	 * @return The domain management service.
	 */
	DomainManager getDomainManager();
}
