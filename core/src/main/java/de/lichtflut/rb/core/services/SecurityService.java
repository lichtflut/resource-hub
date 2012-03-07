/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.List;
import java.util.Locale;

import org.arastreju.sge.security.Domain;
import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.messaging.EmailConfiguration;
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
	 */
	RBUser createDomainAdmin(Domain domain, String email, String username, String password);
	
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
	 * Returns the salt that was used for this users password encryption.
	 * @param user The user.
	 * @return The salt.
	 */
	String getSalt(User user);
	
	/**
	 * Sets a new Password for a {@link User}.
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
	void resetPasswordForUser(RBUser user, EmailConfiguration conf, Locale locale) throws RBException;

	// ----------------------------------------------------
	
	/**
	 * Assure that the user has exactly the given roles.
	 * @param user The user.
	 * @param roles The roles.
	 */
	void setUserRoles(User user, List<String> roles);
	
	/**
	 * Removes all roles from a user.
	 * @param user The user the roles should be removed from.
	 */
	void removeAllUserRoles(final User user);

}
