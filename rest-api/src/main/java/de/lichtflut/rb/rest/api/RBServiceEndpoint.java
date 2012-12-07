/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.security.AuthorizationHandler;
import de.lichtflut.rb.rest.api.security.OperationTypes;
import de.lichtflut.rb.rest.delegate.providers.RBServiceProviderFactory;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author nbleisch
 * 
 */
public abstract class RBServiceEndpoint implements OperationTypes{

	static final String DOMAIN_ID_PARAM = "domainID";
	static final String AUTH_TOKEN = "TOKEN";

    // ----------------------------------------------------

	private static final Logger LOGGER = LoggerFactory.getLogger(RBServiceEndpoint.class);

    // -- DEPENDENCIES ------------------------------------

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
	private RBServiceProviderFactory factory;
	
	@Autowired
	protected AuthModule authModule;

    @Autowired
    protected RBConfig config;
	
	// ----------------------------------------------------

    protected RBUser authenticateUser(String token) throws UnauthenticatedUserException {
        RBUser user=null;
        if (token == null) {
            LOGGER.warn("Request has no session token.");
            throw new UnauthenticatedUserException("No session token.");
        }
        if (token != null) {
            AuthenticationService authService = authModule.getAuthenticationService();
            user = authService.loginByToken(token);
        }
        if (user == null) {
            LOGGER.warn("Detected invalid token: {}", token);
            throw new UnauthenticatedUserException("Token is invalid.");
        }
        return user;
    }

	protected ServiceProvider getProvider(String domainID, RBUser user) {
		return factory.createServiceProvider(domainID, user);
	}

	protected AuthorizationHandler getAuthHandler() {
		return handler;
	}

    // ----------------------------------------------------

    protected List<ResourceNode> findResourcesByType(ModelingConversation conversation, ResourceID type) {
        final Query query = conversation.createQuery();
        query.addField(RDF.TYPE, type);
        return query.getResult().toList(2000);
    }
}
