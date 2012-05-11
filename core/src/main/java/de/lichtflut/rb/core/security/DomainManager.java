/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.util.Collection;
import java.util.Set;

/**
 * <p>
 *  Manager for domains.
 * </p>
 *
 * <p>
 * 	Created May 11, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface DomainManager {
	
	/**
	 * Find a domain by it's unique name.
	 * @param name The name. 
	 * @return The domain object or null.
	 */
	RBDomain findDomain(String domain);

	/**
	 * Register a new domain.
	 * @param domain The domain.
	 */
	RBDomain registerDomain(RBDomain domain);

	/**
	 * Update a domain.
	 * @param domain The domain.
	 */
	void updateDomain(RBDomain domain);

	/**
	 * Delete a domain.
	 * @param domain The domain.
	 */
	void deleteDomain(RBDomain domain);
	
	// ----------------------------------------------------

	/**
	 * Get all registered domain.
	 * @return All domains.
	 */
	Collection<RBDomain> getAllDomains();
	
	/**
	 * Get all registered domain.
	 * @return All domains.
	 */
	Collection<RBUser> loadUsers(String domain, int offset, int max);
	
	// ----------------------------------------------------
	
	/**
	 * Register a role with the corresponding permissions for a specific domain.
	 * @param domain The domain.
	 * @param role The role.
	 * @param permissions The permissions.
	 */
	void registerRole(String domain, String role, Set<String> permissions);

}