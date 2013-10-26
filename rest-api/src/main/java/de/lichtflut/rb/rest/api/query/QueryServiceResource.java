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
package de.lichtflut.rb.rest.api.query;

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.api.common.LinkRVO;
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
import java.util.List;

/**
 * <p>
 *  HTTP resource providing concrete query links.
 * </p>
 *
 * <p>
 *  Created Oct 25, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("domains/{domain}/query")
public class QueryServiceResource extends RBServiceEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listContexts (
            @PathParam(value = "domain") String domainID,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException, IOException {

        authenticateUser(token);

        List<LinkRVO> links = new ArrayList<LinkRVO>();
        links.add(linkToService("entities", EntityQueryService.class, domainID, "term", "type", "scope"));

        return Response.ok(links).build();

    }

    // ----------------------------------------------------

    private LinkRVO linkToService(String name, Class serviceClass, String domainID, String... params) {
        UriBuilder builder = getUriBuilder().path(serviceClass);
        URI uri = builder.build(domainID);
        StringBuilder sb = new StringBuilder(uri.toString());
        boolean first = true;
        for (String param : params) {
            if (first) {
                sb.append("?");
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(param).append("={").append(param).append("}");
        }
        return new LinkRVO(name, sb.toString());
    }

}
