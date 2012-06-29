/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.ServiceProviderFactory;
import de.lichtflut.rb.rest.api.security.AuthorizationHandler;
import de.lichtflut.rb.rest.api.security.OperationTypes;

/**
 * @author nbleisch
 * 
 */
public abstract class RBServiceEndpoint implements OperationTypes{

	static final String DOMAIN_ID_PARAM = "domainID";
	static final String ROOT_USER = "root";
	static final String AUTH_TOKEN = "TOKEN";

	/**
	 * Instance of {@link Logger}
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * An instance of {@link AuthorizationHandler} which is required to
	 * handle all the authorization-stuff
	 */
	@Autowired
	private AuthorizationHandler handler;
	
	/**
	 * An instance of {@link ServiceProvider} which offers several necessary
	 * RB-Services to get this service running.
	 */
	@Autowired
	private ServiceProviderFactory factory;
	
	@Autowired
	protected AuthModule authModule;
	
	// ----------------------------------------------------

	/**
	 * 
	 * @param domainID
	 * @return
	 */
	protected ServiceProvider getProvider(String domainID, RBUser user) {
		ServiceContext context = new ServiceContext(null);
		context.setDomain(domainID);
		context.setUser(user);
		return factory.createServiceProvider(context);
	}

	protected Logger getLog() {
		return log;
	}

	protected RBUser authenticateUser(String token) {
		RBUser user=null;
		if (token != null) {
			AuthenticationService authService = authModule.getAuthenticationService();
			user = authService.loginByToken(token);
		}
		if (user == null) {
			getLog().info(
					"No user could be found for the following secure-token: " + token);
		}
		return user;
	}
	
	
	protected AuthorizationHandler getAuthHandler() {
		return handler;
	}
}