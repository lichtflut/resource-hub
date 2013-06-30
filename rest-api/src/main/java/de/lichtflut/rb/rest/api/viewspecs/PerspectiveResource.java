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

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.writer.impl.PerspectiveWriterImpl;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.naming.QualifiedName;
import org.springframework.stereotype.Component;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

        ServiceProvider provider = getProvider(domain, user);

        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ViewSpecificationService service = provider.getViewSpecificationService();

        if (qn != null) {
            write(buffer, findByQN(service, QualifiedName.fromURI(qn)));
        } else if (id != null) {
            write(buffer, findByID(service, id));
        } else {
            //get all
        }
        return Response.ok(buffer.toString()).build();
    }

    // ----------------------------------------------------

    protected void write(OutputStream out, Perspective... perspectives) {
        PerspectiveWriterImpl writer = new PerspectiveWriterImpl();
        for (Perspective perspective : perspectives) {
            System.err.println("Writing perspective:" + perspective);
            writer.write(perspective, null, out);
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

}
