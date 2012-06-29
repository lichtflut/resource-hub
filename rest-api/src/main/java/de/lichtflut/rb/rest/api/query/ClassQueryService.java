/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.query;

import de.lichtflut.rb.core.common.TermSearcher;
import de.lichtflut.rb.core.common.TermSearcher.Mode;
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
@Path("query/domains/{domain}/classes")
public class ClassQueryService extends AbstractQueryService {

	private static final Logger logger = LoggerFactory.getLogger(ClassQueryService.class);

	public ClassQueryService(){}
	
	// ----------------------------------------------------
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(
			@QueryParam(value="term") String term, 
			@QueryParam(value="superclass") String superclass,
			@PathParam(value = "domain") String domain,
			@CookieParam(value=AuthModule.COOKIE_SESSION_AUTH) String token)
	{
		RBUser user = authModule.getAuthenticationService().loginByToken(token);
		if (user == null) {
			logger.info("Unauthenticated class query.");
			return Response.status(Status.FORBIDDEN).build();
		}
		if (term == null) {
			logger.info("Invalid class query (term is null).");
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		
		Query query = createQuery(domain, user);
		String superclassDecoded = decodeBase64(superclass);
		
		logger.info("Querying classes '{}'  with superclass {}.", term, superclassDecoded);
		
		List<ResultItemRVO> result = performQuery(query, term, superclassDecoded);
		return Response.ok(result).build();
	}
	
	// ----------------------------------------------------
	
	private List<ResultItemRVO> performQuery(Query query, String term, String type) {
		new TermSearcher().prepareQuery(query, term, Mode.SUB_CLASS, type);

        return buildResult(query.getResult());
	}

}
