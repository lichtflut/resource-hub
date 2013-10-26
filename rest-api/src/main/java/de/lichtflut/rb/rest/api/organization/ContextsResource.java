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
package de.lichtflut.rb.rest.api.organization;

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.api.common.LinkRVO;
import de.lichtflut.rb.rest.api.graphs.GraphResource;
import org.arastreju.sge.context.Context;
import org.springframework.stereotype.Component;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  HTTP resource representing a domain's contexts.
 * </p>
 *
 * <p>
 *  Created Oct 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("domains/{domain}/contexts")
public class ContextsResource extends RBServiceEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listContexts (
            @PathParam(value = "domain") String domain,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException, IOException {

        RBUser user = authenticateUser(token);

        Collection<Context> contexts = getOrganizer(domain, user).getContexts();
        return Response.ok(createRVOs(domain, contexts)).build();
    }

    // ----------------------------------------------------

    private List<ContextRVO> createRVOs(String domain, Collection<Context> contexts) {
        List<ContextRVO> result = new ArrayList<ContextRVO>(contexts.size());
        for (Context context : contexts) {
            ContextRVO rvo = new ContextRVO();
            rvo.setQualifiedName(context.getQualifiedName().toURI());
            rvo.getLinks().add(linkTo(domain, context));
            result.add(rvo);
        }
        return result;
    }

    private LinkRVO linkTo(String domain, Context ctx) {
        UriBuilder builder = getUriBuilder()
                .path(GraphResource.class)
                .segment("contexts", ctx.getQualifiedName().getSimpleName());
        URI uri = builder.build(domain);
        return new LinkRVO("content", uri.toString());
    }

}
