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

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.context.Context;
import org.springframework.stereotype.Component;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * <p>
 *  HTTP resource for view specifications.
 * </p>
 *
 * <ul>
 *     <li>/viewspecs => overview of all registered </li>
 * </ul>
 *
 * <p>
 * 	Created June 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("domains/{domain}/viewspecs")
public class ViewSpecResource extends RBServiceEndpoint {

    @GET
    @Produces("text/plain")
    public Response getOverview(
            @PathParam(value = "domain") String domain,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException, IOException {
        RBUser user = authenticateUser(token);
        return Response.ok().build();
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
