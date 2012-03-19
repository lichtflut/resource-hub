/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;

/**
 * <p>
 *  Interface for a component that supports browsing to another entity.
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface IBrowsingHandler {
	
	void createReferencedEntity(EntityHandle handle, ResourceID predicate);

}