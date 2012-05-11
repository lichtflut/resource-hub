/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import org.arastreju.sge.security.LoginException;


/**
 * <p>
 * 	Interface for login related operations.
 * </p>
 *
 * <p>
 * 	Created: Aug 10, 2011
 * </p>
 *
 * @author Ravi Knox
 */
public interface AuthenticationService {

	/**
	 * Try to login the given user.
	 * @param loginData
	 * @return The logged in user.
	 * @throws LoginException when login wasn't successful.
	 */
	RBUser login(LoginData loginData) throws LoginException;

	/**
	 * Log a user in by it's 'remember me' token.
	 * @param token The token from the cookie.
	 * @return The logged in user or null if not valid.
	 */
	RBUser loginByToken(String token);
	
	/**
	 * Create the cookie token for the 'remember me' feature.
	 * @param user The user.
	 * @param loginData The login data used.
	 * @return The token to be saved as cookie.
	 */
	String createRememberMeToken(RBUser user, LoginData loginData);

}
