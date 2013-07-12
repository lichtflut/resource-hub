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
package de.lichtflut.rb.rest.api;

import com.sun.jersey.api.container.ContainerException;
import com.sun.jersey.api.core.ResourceContext;
import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.AuthenticationService;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.rest.api.security.AuthorizationHandler;
import de.lichtflut.rb.rest.api.security.OperationTypes;
import de.lichtflut.rb.rest.delegate.providers.RBServiceProviderFactory;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.organize.Organizer;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * @author nbleisch
 */
public abstract class RBServiceEndpoint implements OperationTypes{

	private static final Logger LOGGER = LoggerFactory.getLogger(RBServiceEndpoint.class);

    // -- DEPENDENCIES ------------------------------------

    @Context
	private UriInfo uriInfo;
	
	@Context
	private ResourceContext resourceContext;
	
	/**
	 * An instance of {@link ServiceProvider} which offers several necessary
	 * RB-Services to get this service running.
	 */
	@Autowired
	private RBServiceProviderFactory factory;

    /**
     * An instance of {@link AuthorizationHandler} which is required to
     * handle all the authorization-stuff
     */
    @Autowired
    private AuthorizationHandler handler;
	
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
        AuthenticationService authService = authModule.getAuthenticationService();
        user = authService.loginByToken(token);
        if (user == null) {
            LOGGER.warn("Detected invalid token: {}", token);
            throw new UnauthenticatedUserException("Token is invalid.");
        }
        return user;
    }
    
    protected String getSelfReference(){
    	return getPath(this.getClass());
    }
    
    protected String getPath(Class clazz){
        return uriInfo.getBaseUriBuilder()
                .path(clazz)
                .build().toString();
    }

	protected ServiceProvider getProvider(String domainID, RBUser user) {
		return factory.createServiceProvider(domainID, user);
	}

    protected ConversationFactory getConversationFactory(String domainID, RBUser user) {
        ServiceContext ctx = new ServiceContext(config, domainID, user);
        return new ArastrejuResourceFactory(ctx);
    }

    protected Organizer getOrganizer(String domainID, RBUser user) {
        ServiceContext ctx = new ServiceContext(config, domainID, user);
        return new ArastrejuResourceFactory(ctx).getOrganizer();
    }

	protected AuthorizationHandler getAuthHandler() {
		return handler;
	}

    // ----------------------------------------------------

    protected List<ResourceNode> findResourcesByType(Conversation conversation, ResourceID type) {
        final Query query = conversation.createQuery();
        query.addField(RDF.TYPE, type);
        return query.getResult().toList(2000);
    }

}
