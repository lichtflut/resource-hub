/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.List;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.Namespace;

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

	List<Namespace> getNamespaces();

	void registerNamespace(NamespaceDeclaration namespace);

	// ----------------------------------------------------

	/**
	 * @return The contexts for the domain.
	 */
	List<Context> getContexts();

	/**
	 * @param context The context to be registered.
	 */
	void registerContext(ContextDeclaration context);

	// ----------------------------------------------------

	/**
	 * Link the domain to it's organisation.
	 * @param organization
	 */
	void setDomainOrganization(final ResourceID organization);

	/**
	 * Get the organization linked to the domain.
	 * @return The Organization.
	 */
	ResourceID getDomainOrganization();

	// ----------------------------------------------------

	/**
	 * Link the current user to the person representing him.
	 * @param person
	 */
	void setUsersPerson(final ResourceID person);

	/**
	 * Get the person representing the current user.
	 * @return The resourceId of the assigned person entity, <code>null</code> if none found.
	 */
	ResourceID getUsersPerson();

}
