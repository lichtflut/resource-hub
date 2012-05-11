/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.arastreju.sge.security.Domain;
import org.springframework.stereotype.Component;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.rest.api.models.generate.SystemDomain;
import de.lichtflut.rb.rest.api.models.generate.SystemIdentity;
import de.lichtflut.rb.rest.api.security.RBOperation;

/**
 * <p>
 * 
 * 
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * 
 */
@Component
@Path("domain/{" + RBServiceEndpoint.DOMAIN_ID_PARAM + "}/")
public class DomainOps extends RBServiceEndpoint {

	@DELETE
	@RBOperation(type = TYPE.DOMAIN_DELETE)
	public Response deleteDomain(@PathParam(DOMAIN_ID_PARAM) String domainID,
			@QueryParam(AUTH_TOKEN) String token) {
		RBUser user = authenticateUser(token);
		if (!getAuthHandler().isAuthorized(user, domainID)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ServiceProvider provider = getProvider(domainID, user);
		try {
			DomainOrganizer dOrganizer = provider.getDomainOrganizer();
			Domain domain = provider.getArastejuGate().getOrganizer()
					.findDomain(domainID);
			dOrganizer.deleteDomain(domain);
			return Response.status(Status.CREATED).build();
		} catch (Exception any) {
			getLog().error(
					"The domain "
							+ domainID
							+ " couldnt be deleted tue to the following exception",
					any);
			return Response.serverError().build();
		}

	}

	/**
	 * <p>
	 * Create a new domain under the following conditions.
	 * 
	 * <ul>
	 * <li>Create
	 * <ul>
	 * <li>Is processed when the given domainID does not exists</li>
	 * <li>The user must be root</li>
	 * <li>A {@link SystemDomain} is not necessary to process the create
	 * operation. The domainAdmin will be the authenticated user, text and
	 * description will be empty</li>
	 * <li>If a {@link SystemDomain} is given the domainID must be equal to
	 * given domainID delivered as PathParam</li>
	 * <li>If the given domain administrator does not exists, a new system user
	 * will be created so long the password and email address is given</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 * @param domainID
	 * @param token
	 * @param domain
	 * @return
	 */
	@POST
	@RBOperation(type = TYPE.DOMAIN_CREATE)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createDomain(@PathParam(DOMAIN_ID_PARAM) String domainID,
			@QueryParam(AUTH_TOKEN) String token, SystemDomain domain) {

		// Check the equality of domainID's
		if (domain != null && domain.getDomainIdentifier() != null
				&& (!domain.getDomainIdentifier().equals(domainID))) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		// Authenticate the user
		RBUser user = authenticateUser(token);
		if (!getAuthHandler().isAuthorized(user, domainID)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		// Check that the domain does not exists
		ServiceProvider provider = getProvider(domainID, user);
		Domain domainNode = provider.getArastejuGate().getOrganizer()
				.findDomain(domainID);
		if (domainNode != null) {
			return Response.status(Status.CONFLICT).build();
		}

		SystemIdentity admin = domain.getDomainAdministrator();
		if (admin != null
				&& (admin.getId() == null || admin.getPassword() == null)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		// Lets process the create
		domainNode = provider.getArastejuGate().getOrganizer().registerDomain(domainID, domain.getTitle(),domain.getDescription());
		if (admin != null) {
			provider.getSecurityService().createDomainAdmin(domainNode,
					admin.getId(), admin.getUsername(), admin.getPassword());
		}

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
	 * <li>if {@link SystemDomain#getDomainID()} is not null, it must be equal
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
			@QueryParam(AUTH_TOKEN) String token, SystemDomain domain) {
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
		// Check if the domain does already exists
		ServiceProvider provider = getProvider(domainID, user);
		Domain domainNode = provider.getArastejuGate().getOrganizer().findDomain(domainID);
		if (domainNode == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		SecurityService secService = provider.getSecurityService();
		SystemIdentity admin = domain.getDomainAdministrator();
		if (admin != null && (admin.getId() == null || admin.getPassword() == null)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		// Lets process the 
		domainNode = provider.getArastejuGate().getOrganizer().registerDomain(domainID, domain.getTitle(),domain.getDescription());
		if (admin != null) {
			secService.createDomainAdmin(domainNode,
					admin.getId(), admin.getUsername(), admin.getPassword());
		}

		return Response.status(Status.CREATED).build();

	}

}
