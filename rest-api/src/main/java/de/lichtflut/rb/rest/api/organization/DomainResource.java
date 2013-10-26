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

import de.lichtflut.rb.RBPermission;
import de.lichtflut.rb.RBRole;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.RBCrypt;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.UserManager;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import de.lichtflut.rb.rest.api.common.LinkRVO;
import de.lichtflut.rb.rest.api.common.UserRVO;
import de.lichtflut.rb.rest.api.infovis.TreeInfoVisService;
import de.lichtflut.rb.rest.api.query.QueryServiceResource;
import org.arastreju.sge.model.Infra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  HTTP Resource for operations on domains.
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * 
 */
@Component
@Path("domains/{domain}")
public class DomainResource extends RBServiceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainResource.class);

    private static List<String> DOMAIN_ADMIN_ROLES = Arrays.asList(
            RBRole.ACTIVE_USER.name(),
            RBRole.IDENTITY_MANAGER.name(),
            RBRole.DOMAIN_ADMIN.name());

    private static List<String> USER_ROLES = Arrays.asList(
            RBRole.ACTIVE_USER.name());

    // ----------------------------------------------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listContexts (
            @PathParam(value = "domain") String domainID,
            @CookieParam(value= AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException, IOException {

        authenticateUser(token);

        DomainManager domainManager = this.authModule.getDomainManager();
        RBDomain rbDomain = domainManager.findDomain(domainID);
        return Response.ok(createRVO(rbDomain)).build();

    }

	@DELETE
	public Response deleteDomain(
            @PathParam("domain") String domain,
            @CookieParam(AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException {
		RBUser user = authenticateUser(token);
        authorizeUser(user, domain, RBPermission.MANAGE_DOMAINS);

		try {
			DomainManager domainManager = this.authModule.getDomainManager();
			RBDomain rbDomain = domainManager.findDomain(domain);
			if (rbDomain != null) {
				domainManager.deleteDomain(rbDomain);
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception any) {
			LOGGER.error("The domain {} couldnt be deleted tue to the following exception", domain);
            LOGGER.error("Error while deleting domain.",any);
			return Response.serverError().build();
		}

	}

	/**
	 * Create a new domain.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createDomain(
            @PathParam("domain") final String domain,
            @CookieParam(AuthModule.COOKIE_SESSION_AUTH) String token)
            throws UnauthenticatedUserException
    {

		// Authenticate the user
		RBUser user = authenticateUser(token);
        authorizeUser(user, AuthModule.MASTER_DOMAIN, RBPermission.MANAGE_DOMAINS);

        DomainManager domainManager = this.authModule.getDomainManager();
		RBDomain rbDomain = domainManager.findDomain(domain);
		if (rbDomain != null) {
			return Response.status(Status.CONFLICT).build();
		}
        rbDomain = new RBDomain();
        rbDomain.setName(domain);
		domainManager.registerDomain(rbDomain);
		return Response.status(Status.CREATED).build();
	}

    /**
     * Add a new admin to this domain.
     */
    @Path("/admins")
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response addDomainAdmin(
            @PathParam("domain") final String domain,
            @CookieParam(AuthModule.COOKIE_SESSION_AUTH) String token,
            UserRVO user)
            throws UnauthenticatedUserException
    {

        RBUser callingUser = authenticateUser(token);
        if (!isInstanceAdmin(callingUser)) {
            authorizeUser(callingUser,domain, RBPermission.GRANT_ADMIN_ACCESS);
        }

        UserManager um = authModule.getUserManagement();
        DomainManager dm = authModule.getDomainManager();

        String id = Infra.coalesce(user.getEmail(), user.getName());
        RBUser existingUser = um.findUser(id);
        RBDomain existingDomain = dm.findDomain(domain);

        try {
            um.setUserRoles(existingUser, existingDomain.getName(), DOMAIN_ADMIN_ROLES);
        } catch (RBAuthException e) {
            Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
        }
        return Response.status(Status.CREATED).build();
    }

    /**
     * Grant a user access to this domain.
     */
    @Path("/users")
    @POST
    @Consumes(MediaType.APPLICATION_JSON )
    public Response grantAccess(
            @PathParam("domain") final String domain,
            @CookieParam(AuthModule.COOKIE_SESSION_AUTH) String token,
            UserRVO userRVO)
            throws UnauthenticatedUserException
    {
        RBUser callingUser = authenticateUser(token);
        if (!isInstanceAdmin(callingUser)) {
            authorizeUser(callingUser, domain, RBPermission.GRANT_USER_ACCESS);
        }

        RBDomain existingDomain = authModule.getDomainManager().findDomain(domain);
        if (existingDomain == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        UserManager um = authModule.getUserManagement();
        String id = Infra.coalesce(userRVO.getEmail(), userRVO.getName());

        try {

            RBUser user = um.findUser(id);
            if (user == null) {
                user = new RBUser();
                user.setDomesticDomain(userRVO.getHomeDomain());
                user.setEmail(userRVO.getEmail());
                user.setUsername(userRVO.getName());
                String encryptedPW = RBCrypt.encryptWithRandomSalt(userRVO.getPassword());
                um.registerUser(user, encryptedPW, userRVO.getHomeDomain());
                LOGGER.info("Registered user '{}' for domain {}", id, domain);
            }
            um.grantAccessToDomain(user, existingDomain);
            um.setUserRoles(user, existingDomain.getName(), USER_ROLES);
        } catch (RBAuthException e) {
            LOGGER.info("Granting access to domain failed for user '{}' due to: {}", id, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Status.CREATED).build();
    }

    // ----------------------------------------------------

    private boolean isInstanceAdmin(RBUser user) {
        Set<String> permissions = authModule.getUserManagement().getUserPermissions(user, AuthModule.MASTER_DOMAIN);
        return permissions.contains(RBPermission.MANAGE_DOMAINS.name());
    }

    // ----------------------------------------------------

    private DomainRVO createRVO(RBDomain domain) {
        DomainRVO rvo = new DomainRVO();
        rvo.setName(domain.getName());
        rvo.setDescription(domain.getDescription());
        rvo.setTitle(domain.getTitle());
        rvo.getLinks().add(linkToContexts(domain.getName()));
        rvo.getLinks().add(linkToInfoVis(domain.getName()));
        rvo.getLinks().add(linkToQueries(domain.getName()));
        return rvo;
    }

    private LinkRVO linkToContexts(String domain) {
        UriBuilder builder = getUriBuilder()
                .path(ContextsResource.class);
        URI uri = builder.build(domain);
        return new LinkRVO("contexts", uri.toString());
    }

    private LinkRVO linkToQueries(String domain) {
        UriBuilder builder = getUriBuilder()
                .path(QueryServiceResource.class);
        URI uri = builder.build(domain);
        return new LinkRVO("query", uri.toString());
    }

    private LinkRVO linkToInfoVis(String domain) {
        UriBuilder builder = getUriBuilder()
                .path(TreeInfoVisService.class);
        URI uri = builder.build(domain);
        return new LinkRVO("infovis", uri.toString() + "?root={root}&type={type}");
    }


}
