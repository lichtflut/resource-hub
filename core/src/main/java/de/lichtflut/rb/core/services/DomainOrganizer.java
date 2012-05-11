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
