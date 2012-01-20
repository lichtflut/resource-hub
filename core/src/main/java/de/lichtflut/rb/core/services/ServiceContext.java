/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.RBConfig;

/**
 * <p>
 *  [DESCRIPTION]
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
	
	private User user;
	
	private String domain;

	// ----------------------------------------------------
	
	/**
	 * @param config
	 */
	public ServiceContext(RBConfig config) {
		this.config = config;
	}
	
	// ----------------------------------------------------

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
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
