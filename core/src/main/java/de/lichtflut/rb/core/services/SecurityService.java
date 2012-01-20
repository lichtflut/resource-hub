/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.List;

import org.arastreju.sge.eh.ArastrejuException;
import org.arastreju.sge.security.Domain;
import org.arastreju.sge.security.User;


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
	 * Create the domain administration user.
	 * @param domain The domain
	 * @return The created user.
	 */
	User createDomainAdmin(Domain domain);
	
	/**
	 * Create a new user.
	 * @param emailID The email as primary identifier.
	 * @param username An optinal username as secondary Identifier.
	 * @param password The unencrypted password.
	 * @return The created user.
	 */
	User createUser(String emailID, String username, String password);

	/**
	 * Sets a new Password for a {@link User}.
	 * @param user
	 * @param currentPassword
	 * @param newPassword
	 */
	void setNewPassword(User user, String currentPassword, String newPassword);

	/**
	 * Resets the password with a generated one.
	 * @param user
	 */
	void resetPasswordForUser(User user);

	/**
	 * Set or change an AlternateID for a user.
	 * @param user The User.
	 * @param alternateID The alternateID for the user.
	 * @throws ArastrejuException i.e. when alternateID already in use. 
	 */
	void setAlternateID(final User user, final String alternateID) throws ArastrejuException;

	/**
	 * Get the AlternateID as String.
	 * @param user The User.
	 * @return The AlternateID as String or empty String if user has none.
	 */
	String getAlternateID(User user);
	
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
