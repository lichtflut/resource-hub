/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.messaging.EmailConfiguration;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;


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
	 * Find a user by one of it's identifiers (username, email address, URI).
	 * @param identifier The identifier.
	 * @return The user or null if not found.
	 */
	RBUser findUser(String identifier);
	
	/**
	 * Create a new user.
	 * @param emailID The email as primary identifier.
	 * @param username An optional username as secondary Identifier.
	 * @param password The unencrypted password.
	 * @return The created user.
	 * @throws RBException 
	 */
	RBUser createUser(String emailID, String username, String password, EmailConfiguration conf, Locale locale) throws RBException;

	/**
	 * Create a new user for given domain with domain administration permissions.
	 * @param domain The domain.
	 * @param email The user's email.
	 * @param username The user's unique name - may be null.
	 * @param password The password.
	 * @return The domain administration user.
	 * @throws RBAuthException 
	 */
	RBUser createDomainAdmin(RBDomain domain, String email, String username, String password) throws RBAuthException;
	
	/**
	 * @param user The existing user to update.
	 * @throws RBException 
	 */
	void storeUser(RBUser user) throws RBException;
	
	/**
	 * Delete a user from this domain and unregister in master domain.
	 * @param user The user to be deleted.
	 */
	void deleteUser(RBUser user);
	
	// ----------------------------------------------------

	/**
	 * Sets a new Password for a {@link RBUser}.
	 * @param user
	 * @param currentPassword
	 * @param newPassword
	 * @throws RBException if the current password is not valid
	 */
	void changePassword(RBUser user, String currentPassword, String newPassword) throws RBException;

	/**
	 * Resets the password with a generated one.
	 * @param user
	 * @return the new password
	 * @throws RBException 
	 */
	void resetPasswordForUser(RBUser user, Locale locale) throws RBException;

	// ----------------------------------------------------
	

	/**
	 * Get the user's roles.
	 * @param user The user who's roles are requested.
	 * @param domain The domain.
	 * @return The list of roles.
	 */
	List<String> getUserRoles(RBUser user, String domain);
	
	/**
	 * Get the user's permissions.
	 * @param user The user who's permissions are requested.
	 * @param domain The domain.
	 * @return The list of permissions.
	 */
	Set<String> getUserPermissions(RBUser user, String domain);
	
	/**
	 * Assure that the user has exactly the given roles.
	 * @param user The user.
	 * @param domain TODO
	 * @param roles The roles.
	 * @throws RBAuthException 
	 */
	void setUserRoles(RBUser user, String domain, List<String> roles) throws RBAuthException;
	
	/**
	 * Removes all roles from a user.
	 * @param user The user the roles should be removed from.
	 * @throws RBAuthException 
	 */
	void removeAllUserRoles(RBUser user) throws RBAuthException;
	
}
