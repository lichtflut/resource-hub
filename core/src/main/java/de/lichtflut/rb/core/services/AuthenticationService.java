/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.security.LoginException;
import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.security.LoginData;

/**
 * <p>
 * 	Interface for all login and authentication related services.
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
	User login(LoginData loginData) throws LoginException;

	/**
	 * Create the cookie token for the 'remember me' feature.
	 * @param user The user.
	 * @return The token to be saved as cookie.
	 */
	String createRememberMeToken(User user);
	
	/**
	 * Log a user in by it's 'remember me' token.
	 * @param token The token from the cookie.
	 * @return The logged in user or null if not valid.
	 */
	User loginByToken(String token);

}