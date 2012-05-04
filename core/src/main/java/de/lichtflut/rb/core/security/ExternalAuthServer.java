/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.util.Collection;


/**
 * <p>
 *  Basic interface to an external authentications server.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ExternalAuthServer {

	/**
	 * Register user with his domain.
	 * @param user The user.
	 * @param domain The user's domain.
	 */
	void registerUser(RBUser user, String domain);

	/**
	 * The user's login IDs have to be mapped to the auth service.
	 * @param existing The existing user.
	 * @param updated The updated user.
	 * @param domain The user's domain.
	 */
	void updateUserIdentity(RBUser existing, RBUser updated, String domain);

	/**
	 * The user's login IDs have to be mapped to the auth service.
	 * @param user The user.
	 */
	void deleteUser(RBUser user);
	
	// ----------------------------------------------------
	
	/**
	 * @return all domains known to this server.
	 */
	public Collection<RBDomain> getAllDomains();

}