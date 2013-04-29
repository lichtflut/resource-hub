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
package de.lichtflut.rb.core.security;

/**
 * <p>
 *  Base interface for the pluggable auth module of an RB application.
 *  This module givea access to 
 *  <ul>
 *  	<li>The authentication service</li>
 *  	<li>The user management service</li>
 *  	<li>The domain management service</li>
 *  </ul>
 * </p>
 *
 * <p>
 * 	Created May 11, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface AuthModule {
	
	String MASTER_DOMAIN = "root";
	
	String COOKIE_REMEMBER_ME = "lfrb-remember-me-id";
	
	String COOKIE_SESSION_AUTH = "lfrb-session-auth";
	
	// ----------------------------------------------------
	
	/**
	 * Get the service for authentication of users.
	 * @return The authentication service.
	 */
	AuthenticationService getAuthenticationService();
	
	/**
	 * Get the service for management of users.
	 * @return The user management service.
	 */
	UserManager getUserManagement();

	/**
	 * Get the service for management of domains and their roles and permissions.
	 * @return The domain management service.
	 */
	DomainManager getDomainManager();
}
