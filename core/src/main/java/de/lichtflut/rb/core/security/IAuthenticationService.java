/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

/**
 * Interface for all login and authentification related services.
 *
 * Created: Aug 10, 2011
 *
 * @author Ravi Knox
 */
public interface IAuthenticationService {

	/**
	 * Authenticate a user by username and password.
	 * @param username - provided username
	 * @param password - provided password
	 * @return true if user is authenticated successful, false if not
	 */
	boolean authenticateUser(String username, String password);

	/**
	 * Log out a authenticated User.
	 * @return true if user was logged out successful, false if not
	 */
	boolean logoutUser();

	/**
	 * Register a new {@link IUser}.
	 * @param user - new User
	 * @return true if {@link IUser} was registered sucessful, false if not
	 */
	boolean registerNewUser(IUser user);
}
