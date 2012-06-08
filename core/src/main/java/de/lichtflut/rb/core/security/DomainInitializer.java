/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;


/**
 * <p>
 *  Initializer for new Domains.
 * </p>
 *
 * <p>
 * 	Created May 11, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface DomainInitializer {
	
	/**
	 * Initialize a domain in the AuthModule.
	 * @param domain The domain.
	 * @param domainManager The auth module.
	 */
	void initialize(RBDomain domain, DomainManager domainManager);

}
