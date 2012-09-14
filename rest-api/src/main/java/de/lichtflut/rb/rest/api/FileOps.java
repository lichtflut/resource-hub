/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.FileService;

/**
 * <p>
 * This service provides RESTful access to the RB-Datastore.
 * </p>
 * Created: Aug 20, 2012
 *
 * @author Ravi Knox
 */
@Component
@Path("content")
public class FileOps extends RBServiceEndpoint {

	private static Logger LOGGER = LoggerFactory.getLogger(FileOps.class);

	// ------------------------------------------------------

	@GET
	@Path("{path:.+}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile(
			@PathParam("path") final String path,
			@QueryParam("domain") final String domain,
			@CookieParam(value=AuthModule.COOKIE_SESSION_AUTH) final String token){
		LOGGER.debug("Requesting File {} from domain{} with token {}", new Object[]{path, domain, token});
		RBUser user = null;
		try {
			user = authenticateUser(token);
		} catch (UnauthenticatedUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileService fileService = this.getProvider(domain, user).getFileService();
		ResponseBuilder rsb = Response.ok(fileService.getData(path).getData());
		LOGGER.debug("Served File {} from domain{} with token {}", new Object[]{path, domain, token});
		return rsb.build();
	}
}
