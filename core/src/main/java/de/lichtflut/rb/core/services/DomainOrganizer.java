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
