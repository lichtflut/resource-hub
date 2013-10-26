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

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.common.TermSearcher;
import de.lichtflut.rb.core.common.TermSearcher.Mode;
import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import org.arastreju.sge.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

/**
 * <p>
 *  Resource for querying of RDF classes.
 * </p>
 *
 * <p>
 * 	Created Jun 28, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("domains/{domain}/query/classes")
public class ClassQueryService extends AbstractQueryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassQueryService.class);

	public ClassQueryService(){}
	
	// ----------------------------------------------------
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(
			@QueryParam(value="term") String term, 
			@QueryParam(value="superclass") String superclass,
			@PathParam(value = "domain") String domain,
			@CookieParam(value=AuthModule.COOKIE_SESSION_AUTH) String token) throws UnauthenticatedUserException
    {
        RBUser user = authenticateUser(token);
		if (term == null) {
			LOGGER.info("Invalid class query (term is null).");
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		Query query = createQuery(domain, user, RBSystem.TYPE_SYSTEM_CTX);
		String superclassDecoded = decodeBase64(superclass);
		
		LOGGER.info("Querying classes '{}'  with superclass {}.", term, superclassDecoded);
		
		List<ResultItemRVO> result = performQuery(query, term, superclassDecoded);
		return Response.ok(result).build();
	}
	
	// ----------------------------------------------------
	
	private List<ResultItemRVO> performQuery(Query query, String term, String type) {
		new TermSearcher(query).prepareQuery(term, Mode.SUB_CLASS, type);

        return buildResult(query.getResult());
	}

}
