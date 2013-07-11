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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

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

    @POST
    @Path("context/{ctx}")
    @Consumes({MediaType.APPLICATION_XML})
    public void uploadToContext(
            @PathParam(value = "domain") String domain,
            @PathParam(value = "ctx") String context,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token,
            InputStream in)
        throws UnauthenticatedUserException
    {

        final RBUser user = authenticateUser(token);

        LOGGER.info("Uploading to domain {} and context {}.", domain, context);

    }

}
