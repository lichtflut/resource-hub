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
import de.lichtflut.rb.core.security.RBDomain;
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
 *  HTTP resource listing all domains.
 * </p>
 *
 * <p>
 *  Created July 22, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("domains")
public class DomainsOverviewResource extends RBServiceEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDomains (
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException, IOException {

        RBUser user = authenticateUser(token);

        Collection<RBDomain> domains = authModule.getDomainManager().getDomainsForUser(user);

        return Response.ok(createRVOs(domains)).build();
    }

    // ----------------------------------------------------

    private List<DomainRVO> createRVOs(Collection<RBDomain> domains) {
        List<DomainRVO> result = new ArrayList<DomainRVO>(domains.size());
        for (RBDomain domain : domains) {
            DomainRVO rvo = new DomainRVO();
            rvo.setName(domain.getName());
            rvo.setTitle(domain.getTitle());
            rvo.setDescription(domain.getDescription());
            rvo.getLinks().add(linkTo(domain));
            result.add(rvo);
        }
        return result;
    }

    private LinkRVO linkTo(RBDomain domain) {
        UriBuilder builder = getUriBuilder().path(DomainResource.class);
        URI uri = builder.build(domain.getName());
        return new LinkRVO("content", uri.toString());
    }

}
