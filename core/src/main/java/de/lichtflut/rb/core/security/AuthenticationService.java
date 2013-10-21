/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.security;

import de.lichtflut.rb.core.eh.LoginException;

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
	 * @param loginData The provided login data.
	 * @return The logged in user.
	 * @throws LoginException when login wasn't successful.
	 */
	RBUser login(LoginData loginData) throws LoginException;

    /**
     * Log an (already authenticated) user in just by it's name.
     * @param username The username.
     * @return The logged in user.
     * @throws LoginException when user not exist or login forbidden.
     */
    RBUser login(String username) throws LoginException;

	/**
	 * Log a user in by it's 'remember me' token.
	 * @param token The token from the cookie.
	 * @return The logged in user or null if not valid.
	 */
	RBUser loginByToken(String token);
	
	/**
	 * Create the cookie token for the 'remember me' feature.
	 * @param user The user.
	 * @return The token to be saved as cookie.
	 */
	String createSessionToken(RBUser user);
	
	/**
	 * Create the cookie token for the 'remember me' feature.
	 * @param user The user.
	 * @param loginData The login data used.
	 * @return The token to be saved as cookie.
	 */
	String createRememberMeToken(RBUser user, LoginData loginData);

}
