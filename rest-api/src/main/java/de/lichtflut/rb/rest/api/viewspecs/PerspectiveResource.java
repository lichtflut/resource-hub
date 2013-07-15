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
package de.lichtflut.rb.rest.api.viewspecs;

import com.sun.jersey.core.util.Base64;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.io.writers.CommonFormatWriter;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.reader.VSpecImporter;
import de.lichtflut.rb.core.viewspec.writer.impl.PerspectiveWriterImpl;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.naming.QualifiedName;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  HTTP resource for perspectives.
 * </p>
 *
 *
 * <p>
 * 	Created June 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("domains/{domain}/viewspecs/perspectives")
public class PerspectiveResource extends RBServiceEndpoint {

    @GET
    @Produces("text/plain")
    public Response getPerspectives(
            @QueryParam(value="id") String id,
            @QueryParam(value="qn") String qn,
            @PathParam(value = "domain") String domain,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException, IOException {
        RBUser user = authenticateUser(token);

        final ViewSpecificationService service = getProvider(domain, user).getViewSpecificationService();
        final List<Perspective> perspectives = find(service, qn, id);
        if (perspectives == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final CommonFormatWriter writer = new CommonFormatWriter(new NamespaceMap(), buffer);
        write(writer, perspectives);
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

        final ViewSpecificationService service = getProvider(domain, user).getViewSpecificationService();

        VSpecImporter importer = new VSpecImporter(service);
        importer.doImport(in);
        in.close();

        URI newURI = UriBuilder.fromResource(PerspectiveResource.class).build();
        return Response.created(newURI).build();
    }

    // ----------------------------------------------------

    protected void write(CommonFormatWriter out, List<Perspective> perspectives) {
        PerspectiveWriterImpl writer = new PerspectiveWriterImpl();
        for (Perspective perspective : perspectives) {
            writer.write(perspective, null, out);
        }
    }

    protected List<Perspective> find(ViewSpecificationService service, String qn, String id) {
        if (qn != null) {
            return listIfNotNull(findByQN(service, restore(qn)));
        } else if (id != null) {
            return listIfNotNull(findByID(service, id));
        } else {
            //get all
            return new ArrayList<Perspective>();
        }
    }

    protected Perspective findByQN(ViewSpecificationService service, QualifiedName qn) {
        return service.findPerspective(qn);
    }

    protected Perspective findByID(ViewSpecificationService service, String id) {
        QualifiedName qn = QualifiedName.from(RBSystem.PERSPECTIVES_NAMESPACE_URI, id);
        return service.findPerspective(qn);
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

    private List<Perspective> listIfNotNull(Perspective perspective) {
        if (perspective != null) {
            return Collections.singletonList(perspective);
        } else {
            return null;
        }
    }

    private QualifiedName restore(String qn) {
        if (qn.contains(":")) {
            return QualifiedName.from(qn);
        } else {
            // Seems to be Base64 encoded
            return QualifiedName.from(Base64.base64Decode(qn));
        }
    }

}
