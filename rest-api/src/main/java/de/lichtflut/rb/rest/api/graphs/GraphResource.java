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
package de.lichtflut.rb.rest.api.graphs;

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.ContextID;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.io.StatementContainer;
import org.arastreju.sge.io.StatementStorer;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.organize.Organizer;
import org.arastreju.sge.persistence.TransactionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.arastreju.sge.context.ContextID.localContext;

/**
 * <p>
 *  HTTP resource for semantic graphs.
 * </p>
 *
 * <p>
 * 	Created Jul 11, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("domains/{domain}/graphs")
public class GraphResource extends RBServiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphResource.class);

    // ----------------------------------------------------

    @GET
    @Path("context/{ctx}")
    @Produces({MediaType.APPLICATION_XML})
    public StreamingOutput getContext(
            @PathParam(value = "domain") final String domain,
            @PathParam(value = "ctx") final String ctxName,
            @CookieParam(value = AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException {

        final RBUser user = authenticateUser(token);

        LOGGER.info("Writing context {} from domain {}.", ctxName, domain);

        return new StreamingOutput() {
            @Override
            public void write(OutputStream out) throws IOException, WebApplicationException {
                StatementContainer container = getOrganizer(domain, user).getStatements(localContext(ctxName));
                try {
                    new RdfXmlBinding().write(container, out);
                } catch (SemanticIOException e) {
                    throw new WebApplicationException(e);
                }
            }
        };
    }

    @POST
    @Path("context/{ctx}")
    @Consumes({MediaType.APPLICATION_XML})
    public void uploadToContext(
            @PathParam(value = "domain") String domain,
            @PathParam(value = "ctx") String ctxName,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token,
            InputStream in)
            throws UnauthenticatedUserException, SemanticIOException, IOException {

        final RBUser user = authenticateUser(token);

        LOGGER.info("Uploading to domain {} and context {}.", domain, ctxName);

        Context ctx = localContext(ctxName);

        Conversation conversation = conversation(domain, user, ctx);
        TransactionControl tx = conversation.beginTransaction();
        try {
            new RdfXmlBinding().read(in, new StatementStorer(conversation));
            tx.success();
        } finally {
            tx.finish();
            conversation.close();
        }
    }

    // ----------------------------------------------------

    private Conversation conversation(String domain, RBUser user, Context context) {
        final ServiceProvider provider = getProvider(domain, user);
        return provider.getConversation(context);
    }

}
