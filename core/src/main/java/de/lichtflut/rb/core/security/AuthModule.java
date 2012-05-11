/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

/**
 * <p>
 *  Interface for a pluggable auth module.
 * </p>
 *
 * <p>
 * 	Created May 11, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface AuthModule {
	
	AuthenticationService getAuthenticationService();
	
	UserManager getUserManagement();

	DomainManager getDomainManager();
}
