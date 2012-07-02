/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.common;

/**
 * <p>
 *  The well known roles in Glasnost. This are not the roles themself, but only the names
 *  of the roles supported in Glasnost.
 * </p>
 *
 * <p>
 * 	Created Dec 23, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public enum RBRole {

	// active users may login
	ACTIVE_USER(RBPermission.LOGIN),
	
	// admin of the whole instance, may administer the domains
	INSTANCE_ADMIN(RBPermission.LOGIN, 
			RBPermission.MANAGE_DOMAINS, 
			RBPermission.ENTER_ADMIN_AREA),
	
	// enter the admin area
	DOMAIN_ADMIN(RBPermission.LOGIN,
			RBPermission.ENTER_ADMIN_AREA),
	
	// Manager of users and their roles
	IDENTITY_MANAGER(
			RBPermission.ENTER_ADMIN_AREA,
			RBPermission.LOGIN,
			RBPermission.SEE_USERS, 
			RBPermission.CREATE_USERS, 
			RBPermission.DELETE_USERS);

	// ----------------------------------------------------
	
	private final RBPermission[] permissions;

	/**
	 * Constructor.
	 */
	private RBRole(RBPermission... permissions) {
		this.permissions = permissions;
	}
	
	/**
	 * @return the permissions
	 */
	public RBPermission[] getPermissions() {
		return permissions;
	}
	
}
