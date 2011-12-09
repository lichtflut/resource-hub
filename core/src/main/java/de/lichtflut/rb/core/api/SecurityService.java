/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.security.LoginData;

/**
 * <p>
 *  Service for authentication, authorization and identity management.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface SecurityService {
	
	/**
	 * Create a new user.
	 * @param emailID The email as primary identifier.
	 * @param username An optinal username as secondary Identifier.
	 * @param password The unencrypted password.
	 * @return The created user.
	 */
	User createUser(String emailID, String username, String password);
	
	/**
	 * Try to login the given user.
	 * @param loginData
	 * @return
	 */
	User login(LoginData loginData);

}
