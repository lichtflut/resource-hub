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
