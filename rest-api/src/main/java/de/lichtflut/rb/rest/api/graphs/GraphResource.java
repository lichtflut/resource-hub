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

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.context.ContextID;
import org.arastreju.sge.io.N3Binding;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.io.StatementContainer;
import org.arastreju.sge.io.StatementStorer;
import org.arastreju.sge.model.nodes.views.SNContext;
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
import java.util.regex.Pattern;

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

    private static final Pattern NAME_CONSTRAINT = Pattern.compile("[a-zA-Z-_0-9]");

    private static final String RDF_XML = "application/rdf+xml";

    private static final String RDF_N3 = "text/n3";

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
    @Consumes(RDF_XML)
    public void uploadRdfXml(
            @PathParam(value = "domain") String domain,
            @PathParam(value = "ctx") String ctxName,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token,
            InputStream in)
            throws UnauthenticatedUserException, SemanticIOException, IOException {

        uploadRDF(authenticateUser(token), domain, ctxName, in, new RdfXmlBinding());
    }

    @POST
    @Path("context/{ctx}")
    @Consumes(RDF_N3)
    public void uploadN3(
            @PathParam(value = "domain") String domain,
            @PathParam(value = "ctx") String ctxName,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token,
            InputStream in)
            throws UnauthenticatedUserException, SemanticIOException, IOException {

        uploadRDF(authenticateUser(token), domain, ctxName, in, new N3Binding());
    }

    // ----------------------------------------------------

    private void uploadRDF(RBUser user, String domain, String ctxName, InputStream in, SemanticGraphIO io) {

        Context ctx = detectContext(user, domain, ctxName);

        LOGGER.info("Uploading to domain '{}' and context '{}'.", domain, ctx);

        Conversation conversation = conversation(domain, user, ctx);
        TransactionControl tx = conversation.beginTransaction();
        try {
            io.read(in, new StatementStorer(conversation));
            tx.success();
        } catch (SemanticIOException e) {
            tx.fail();
            LOGGER.error("RDF upload failed.", e);
        } catch (IOException e) {
            tx.fail();
            LOGGER.error("RDF upload failed.", e);
        } finally {
            tx.finish();
            conversation.close();
        }
    }

    // ----------------------------------------------------

    private Context detectContext(RBUser user, String domain, String name) {
        if ("default".equals(name)) {
            return RBSystem.DOMAIN_CTX;
        } else if (!NAME_CONSTRAINT.matcher(name).matches()) {
            throw new IllegalArgumentException("Not a valid context name: " + name);
        } else {
            Context ctx = localContext(name);
            return ensureRegistered(user, domain, ctx);
        }
    }

    private Context ensureRegistered(RBUser user, String domain, Context ctx) {
        Organizer organizer = getOrganizer(domain, user);
        Context context = organizer.findContext(ctx.getQualifiedName());
        if (context == null) {
            // Register context and set access context to domain context
            context = organizer.registerContext(ctx.getQualifiedName());
            SNContext.from(context).setAccessContext(ContextID.forContext(ContextID.DOMAIN_CONTEXT));
        }
        return context;
    }

    private Conversation conversation(String domain, RBUser user, Context context) {
        final ServiceProvider provider = getProvider(domain, user);
        return provider.getConversation(context);
    }

}
