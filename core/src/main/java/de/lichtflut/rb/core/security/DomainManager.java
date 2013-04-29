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
	RBDomain findDomain(String name);

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
	 * Get all domains this user may access.
	 * @return All domains for this user.
	 */
	Collection<RBDomain> getDomainsForUser(RBUser user);
	
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