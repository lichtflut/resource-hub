/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.security.RBUser;

/**
 * <p>
 *  Context of the backend services.
 * </p>
 *
 * <p>
 * 	Created Jan 19, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ServiceContext {
	
	private final RBConfig config;
	
	private RBUser user;
	
	private String domain;

	// ----------------------------------------------------
	
	/**
	 * @param config
	 */
	public ServiceContext(RBConfig config) {
		this.config = config;
	}
	
	/**
	 * @param config
	 */
	public ServiceContext(RBConfig config, String domain) {
		this(config);
		this.domain = domain;
	}
	
	// ----------------------------------------------------

	public boolean isAuthenticated() {
		return user != null;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the user
	 */
	public RBUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(RBUser user) {
		this.user = user;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the config
	 */
	public RBConfig getConfig() {
		return config;
	}
	
}
