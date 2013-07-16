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

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.rest.api.RBServiceEndpoint;
import org.arastreju.sge.persistence.TransactionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.security.UserManager;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;
import de.lichtflut.rb.rest.api.models.generate.SystemDomain;
import de.lichtflut.rb.rest.api.models.generate.SystemIdentity;
import de.lichtflut.rb.rest.api.security.RBOperation;

import java.util.Arrays;

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
public class DomainOps extends RBServiceEndpoint {

    static final String DOMAIN_ID_PARAM = "domain";
    static final String AUTH_TOKEN = "TOKEN";

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainOps.class);

    // ----------------------------------------------------

	@DELETE
	@RBOperation(type = TYPE.DOMAIN_DELETE)
	public Response deleteDomain(
            @PathParam(DOMAIN_ID_PARAM) String domainID,
			@QueryParam(AUTH_TOKEN) String token)
            throws UnauthenticatedUserException {
		RBUser user = authenticateUser(token);

		if (!getAuthHandler().isAuthorized(user, domainID)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		try {
			DomainManager domainManager = this.authModule.getDomainManager();
			RBDomain rbDomain = domainManager.findDomain(domainID);
			if (rbDomain != null) {
				domainManager.deleteDomain(rbDomain);
			}
			return Response.status(Status.NO_CONTENT).build();
		} catch (Exception any) {
			LOGGER.error("The domain {} couldnt be deleted tue to the following exception", domainID);
            LOGGER.error("Error while deleting domain.",any);
			return Response.serverError().build();
		}

	}

	/**
	 * Create a new domain.
	 */
	@POST
	@RBOperation(type = TYPE.DOMAIN_CREATE)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createDomain(
            @PathParam(value = "domain") final String domain,
            @CookieParam(value = AuthModule.COOKIE_SESSION_AUTH) String token)
        throws UnauthenticatedUserException
    {

		// Authenticate the user
		RBUser user = authenticateUser(token);
        // TODO: Authorization check

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
     * <p>
     * Updates an existing domain under the following conditions.
     *
     * <li>Update
     * <ul>
     * <li>Is processed when the given domainID does already exists</li>
     * <li>The user has to be domain-admin or root</li>
     * <li>A {@link SystemDomain} must be given to process the update</li>
     * <li>if {@link de.lichtflut.rb.rest.api.models.generate.SystemDomain#getDomainIdentifier()} is not null, it must be equal
     * to the given domainID delivered as PathParam</li>
     * <li>If the given domain administrator does not exists, a new system user
     * will be created so long the password and email address is given</li>
     * </ul>
     * </li>
     * </p>
     *
     * @param domainID
     * @param token
     * @param domain
     * @return
     */
    @PUT
    @RBOperation(type = TYPE.DOMAIN_UPDATE)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response updateDomain(@PathParam(DOMAIN_ID_PARAM) String domainID,
                                 @QueryParam(AUTH_TOKEN) String token, SystemDomain domain) throws UnauthenticatedUserException {
        // Check if domain is not null
        if (domain == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        // Check the equality of domainID's
        if (domain.getDomainIdentifier() != null
                && (!domain.getDomainIdentifier().equals(domainID))) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        // Authenticate the user
        RBUser user = authenticateUser(token);
        if (!getAuthHandler().isAuthorized(user, domainID)) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        DomainManager domainManager = this.authModule.getDomainManager();
        ServiceProvider provider = getProvider(domainID, user);
        RBDomain rbDomain = domainManager.findDomain(domainID);
        if (rbDomain == null) {
            return Response.status(Status.CONFLICT).build();
        }
        rbDomain.setDescription(domain.getDescription());
        rbDomain.setTitle(domain.getTitle());
        rbDomain.setName(domain.getDomainIdentifier());
        SystemIdentity admin = domain.getDomainAdministrator();
        if (admin != null
                && (admin.getId() == null || admin.getPassword() == null)) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        domainManager.updateDomain(rbDomain);

        // Lets process the
        if (admin != null) {
            try {
                provider.getSecurityService()
                        .createDomainAdmin(rbDomain, admin.getId(),
                                admin.getUsername(), admin.getPassword());
            } catch (RBAuthException e) {
                LOGGER.error(
                        "Domain admin couldnt be created due to the following exception",
                        e);
            }
        }

        return Response.status(Status.CREATED).build();

    }

    // ----------------------------------------------------

	/**
	 * @param rbDomain
	 * @param admin
	 * @throws RBAuthException
	 */
	private void addDomainAdmin(RBDomain rbDomain, SystemIdentity admin, ServiceProvider provider)
			throws RBAuthException {
		UserManager uManager = authModule.getUserManagement();
		RBUser user = uManager.findUser(admin.getId());
		if (user == null) {
			provider.getSecurityService().createDomainAdmin(rbDomain,
					admin.getId(), admin.getUsername(), admin.getPassword());
		} else {
			uManager.setUserRoles(user, rbDomain.getName(),
					Arrays.asList(new String[0]));
		}
	}

}
