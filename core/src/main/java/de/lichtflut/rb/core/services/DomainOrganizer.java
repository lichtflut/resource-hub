/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.Collection;
import java.util.List;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.security.Domain;
import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.organizer.ContextDeclaration;
import de.lichtflut.rb.core.organizer.NamespaceDeclaration;

/**
 * <p>
 *  Organization service for a domain.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface DomainOrganizer {
	
	/**
	 * Get the master domain or null if not initialized.
	 * @return The master domain or null.
	 */
	Domain getDomesticDomain();
	
	/**
	 * Get all registered domains.
	 * @return The domains.
	 */
	Collection<Domain> getDomains();
	
	/**
	 * Register a new domain, known by this domain.
	 * @param domain The domain object.
	 * @return The domain.
	 */
	Domain registerDomain(Domain domain);

	/**
	 * Update info for given domain.
	 * @param domain The domain.
	 */
	void updateDomain(Domain domain);
	
	/**
	 * Delete a domain.
	 * @param domain The domain.
	 */
	void deleteDomain(Domain domain);
	
	// ----------------------------------------------------
	
	/**
	 * Create a new user for given domain with domain administration permissions.
	 * @param domain The domain.
	 * @param email The user's email.
	 * @param username The user's unique name - may be null.
	 * @param password The password.
	 * @return The domain administration user.
	 */
	public User createDomainAdmin(Domain domain, String email, String username, String password);
	
	// ----------------------------------------------------
	
	/**
	 * Link the domain to it's organisation.
	 * @param organization
	 */
	void setDomainOrganization(final ResourceID organization);
	
	ResourceID getDomainOrganization();
	
	// ----------------------------------------------------

	/**
	 * @return
	 */
	List<Namespace> getNamespaces();
	
	void registerNamespace(NamespaceDeclaration namespace);

	// ----------------------------------------------------
	
	/**
	 * @return
	 */
	List<Context> getContexts();

	/**
	 * @param object
	 */
	void registerContext(ContextDeclaration object);


}
