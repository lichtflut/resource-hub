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
	Domain getMasterDomain();
	
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
