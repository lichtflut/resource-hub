/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.util.List;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.Namespace;

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
	
	void setDomainOrganization(final ResourceID organization);
	
	ResourceID getDomainOrganization();

	/**
	 * @return
	 */
	List<Namespace> getNamespaces();

	/**
	 * @return
	 */
	List<Context> getContexts();

}
