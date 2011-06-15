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

	ResourceNode getAssociatedResource();

	String getName();

	Set<Role> getRoles();

	Set<Permission> getPermissions();

	boolean isInRole(Role role);

	boolean hasPermission(Permission permission);
}
