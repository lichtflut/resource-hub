/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.util.Set;

import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Base of all identifiable objects with granted permissions.
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Identity {

	/**
	 * TODO: DESCRIPTION.
	 * @return {@link ResourceNode}
	 */
	ResourceNode getAssociatedResource();

	/**
	 * Returns Name.
	 * @return String
	 */
	String getName();

	/**
	 * Returns all Roles.
	 * @return Set containing all Roles
	 */
	Set<Role> getRoles();

	/**
	 * Returns Permissions.
	 * @return Set containing all permissions
	 */
	Set<Permission> getPermissions();

	/**
	 * Returns true if User is in Role.
	 * @param role -
	 * @return boolean true if User is in Role, false if not
	 */
	boolean isInRole(Role role);

	/**
	 * Returns true if User has a specific permission.
	 * @param permission -
	 * @return boolean true if User has the permission, false if not
	 */
	boolean hasPermission(Permission permission);
}
