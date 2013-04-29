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
 * This service provides RESTful access to the FileService.
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
			throw new RuntimeException("User can not be authenticated", e);
		}
		FileService fileService = this.getProvider(domain, user).getFileService();
		ResponseBuilder rsb = Response.ok(fileService.getData(path).getData());
		LOGGER.debug("Served File {} from domain{} with token {}", new Object[]{path, domain, token});
		return rsb.build();
	}
}
