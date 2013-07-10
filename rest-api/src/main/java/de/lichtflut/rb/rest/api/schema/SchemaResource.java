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
package de.lichtflut.rb.rest.api.schema;

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RsfSchemaParser;
import de.lichtflut.rb.core.schema.writer.OutputElements;
import de.lichtflut.rb.core.schema.writer.rsf.RsfWriter;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SchemaExporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.impl.SchemaExporterImpl;
import de.lichtflut.rb.core.services.impl.SchemaImporterImpl;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.SimpleResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * <p>
 *  HTTP resource for schemas.
 * </p>
 *
 * <p>
 * 	Created May 3, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("domains/{domain}/schemas")
public class SchemaResource extends RBServiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchemaResource.class);

    // ----------------------------------------------------

    @GET
    @Produces("text/plain")
    public Response getSchemas(
            @QueryParam(value="type") String type,
            @PathParam(value = "domain") String domain,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException, IOException {
        RBUser user = authenticateUser(token);

        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        final SchemaManager schemaManager = new SchemaManagerImpl(getConversationFactory(domain, user));

        if (type == null) {
            SchemaExporter exporter = new SchemaExporterImpl(schemaManager, new RsfWriter());
            exporter.exportAll(buffer);
        } else {
            ResourceSchema schema = schemaManager.findSchemaForType(new SimpleResourceID(type));
            new RsfWriter().write(buffer, new OutputElements().addSchemas(schema));
        }

        return Response.ok(buffer.toString()).build();
    }

    @POST
    @Produces("text/plain")
    public Response upload(
            @PathParam(value = "domain") String domain,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token,
            InputStream in)
            throws UnauthenticatedUserException, IOException {

        final RBUser user = authenticateUser(token);

        final SchemaManager schemaManager = new SchemaManagerImpl(getConversationFactory(domain, user));
        SchemaImporterImpl importer = new SchemaImporterImpl(schemaManager,
                conversation(domain, user), new RsfSchemaParser());

        IOReport read = importer.read(in);
        LOGGER.info("Imported schemas: \n{}", read);

        URI newURI = UriBuilder.fromResource(SchemaResource.class).build();
        return Response.created(newURI).build();
    }

    // ----------------------------------------------------

    private Conversation conversation(String domain, RBUser user) {
        final ServiceProvider provider = getProvider(domain, user);
        return provider.getConversation();
    }

    private Conversation conversation(String domain, RBUser user, Context context) {
        final ServiceProvider provider = getProvider(domain, user);
        return provider.getConversation(context);
    }

}
