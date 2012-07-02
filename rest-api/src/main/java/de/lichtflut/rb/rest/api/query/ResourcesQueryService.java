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
 *  Resource for querying of properties.
 * </p>
 *
 * <p>
 * 	Created Jun 29, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("query/domains/{domain}/resources")
public class ResourcesQueryService extends AbstractQueryService {

	private static final Logger logger = LoggerFactory.getLogger(ResourcesQueryService.class);

	public ResourcesQueryService(){}
	
	// ----------------------------------------------------
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(
			@QueryParam(value="term") String term,
            @QueryParam(value="type") String type,
			@PathParam(value = "domain") String domain,
			@CookieParam(value=AuthModule.COOKIE_SESSION_AUTH) String token)
	{
		RBUser user = authenticateUser(token);
		if (user == null) {
			logger.info("Unauthenticated resource query.");
			return Response.status(Status.FORBIDDEN).build();
		}
		if (term == null) {
			logger.info("Invalid resource query (term is null).");
			return Response.status(Status.BAD_REQUEST).build();
		}

        Query query = createQuery(domain, user);
        String typeDecoded = decodeBase64(type);

        logger.info("Querying resources '{}' of type {}.", term, typeDecoded);

        List<ResultItemRVO> result = performQuery(query, term, typeDecoded);
        return Response.ok(result).build();
	}
	
	// ----------------------------------------------------

    private List<ResultItemRVO> performQuery(Query query, String term, String type) {
        new TermSearcher().prepareQuery(query, term, Mode.VALUES, type);

        return buildResult(query.getResult());
    }
	
}
