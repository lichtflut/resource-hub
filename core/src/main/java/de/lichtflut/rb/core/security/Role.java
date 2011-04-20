/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.util.Set;

import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Representation of a user's role.
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Role {
	
	/**
	 * Get the resource node associated with this role.
	 * @return The corresponding resource note.
	 */ 
	ResourceNode getAssociatedResource();
	
	/**
	 * Get the unique role name.
	 * @return The name.
	 */
	String getName();

	/**
	 * Get the permissions assigned to this role.
	 * @return The role's permissions.
	 */
	Set<Permission> getPermissions();
	
}
