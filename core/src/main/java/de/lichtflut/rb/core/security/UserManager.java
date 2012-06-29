/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.util.List;
import java.util.Set;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.eh.RBException;


/**
 * <p>
 *  Basic interface to an external authentications server.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface UserManager {

	/**
	 * Find a user by one of it's identifiers (username, email address, URI).
	 * @param identifier The identifier.
	 * @return The user or null if not found.
	 */
	RBUser findUser(String identifier);
	
	/**
	 * Search a user by a term.
	 * @param term The term.
	 * @return The search result containing the matching users.
	 */
	SearchResult<RBUser> searchUsers(String term);
	
	// ----------------------------------------------------
	
	/**
	 * Register a new user with his domain.
	 * @param user The user.
	 * @param credential The new user's credential.
	 * @param domain The user's domain.
	 */
	void registerUser(RBUser user, String credential, String domain) throws RBAuthException;
	
	/**
	 * Update user information.
	 * @param updated The updates user.
	 * @throws RBAuthException
	 */
	void updateUser(RBUser updated) throws RBAuthException;

	/**
	 * Delete a user.
	 * @param user The user.
	 */
	void deleteUser(RBUser user);
	
	/**
	 * Grant an existing user access to another domain.
	 * @param user The user.
	 * @param domain The user's domain.
	 */
	void grantAccessToDomain(RBUser user, RBDomain domain) throws RBAuthException;
	
	/**
	 * Revoke users access to another domain.
	 * @param user The user.
	 * @param domain The user's domain.
	 */
	void revokeAccessToDomain(RBUser user, RBDomain domain) throws RBAuthException;
	
	// ----------------------------------------------------
	
	/**
	 * Sets a new password for a user.
	 * @param user The user.
	 * @param newPassword The new password.
	 */
	void changePassword(RBUser user, String newPassword);
	
	/**
	 * Verifies the password for a user (outside a login).
	 * @param user The user.
	 * @param currentPassword The password to be verifies.
	 * @throws RBException if the current password is not valid
	 */
	void verifyPassword(RBUser user, String currentPassword) throws RBAuthException;

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
	 * Assure that the user has exactly the given roles for a domain.
	 * @param user The user.
	 * @param domain The domain.
	 * @param roles The roles.
	 * @throws RBAuthException 
	 */
	void setUserRoles(RBUser user, String domain, List<String> roles) throws RBAuthException;
	
	/**
	 * Removes all roles from a user for a domain.
	 * @param user The user the roles should be removed from.
	 * @param domain The domain.
	 * @throws RBAuthException 
	 */
	void removeAllUserRoles(RBUser user, String domain) throws RBAuthException;

}