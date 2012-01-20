/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.security.Domain;
import java.util.List;

import org.arastreju.sge.security.Role;
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
	 * Add role(s) to a user.
	 * @param user The user the roles should be added to.
	 * @param roles The role(s) that should be added to the user. 
	 */
	void addRolesToUser(final User user, final Role... roles);
	
	/**
	 * Remove role(s) from a user.
	 * @param user The user the roles should be removed from.
	 * @param roles The role(s) that should be removed from the user. 
	 */
	void removeRolesFromUser(final User user, final Role... roles);
	
	/**
	 * Gets a {@link Role} by its name.
	 * @param name The name of the role.
	 * @return The {@link Role}.
	 */
	Role registerRole(final String name);

	/**
	 * Change (or register) an AlternateID for a user.
	 * @param user The User.
	 * @param alternateID The alternateID for the user.
	 */
	void setAlternateID(final User user, final String alternateID);

	/**
	 * Assure that the user has exactly the given roles.
	 * @param user The user.
	 * @param roles The roles.
	 */
	void setUserRoles(User user, List<String> roles); 
}
