/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.query;

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
 *  Resource for querying of entities.
 * </p>
 *
 * <p>
 * 	Created Jun 22, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("query/domains/{domain}/entities")
public class EntityQueryService extends AbstractQueryService {
	
	private static final Logger logger = LoggerFactory.getLogger(EntityQueryService.class); 

	public EntityQueryService(){}
	
	// ----------------------------------------------------
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(
			@QueryParam(value="term") String term, 
			@QueryParam(value="type") String type,
			@PathParam(value = "domain") String domain,
			@CookieParam(value=AuthModule.COOKIE_SESSION_AUTH) String token) throws UnauthenticatedUserException
    {
		RBUser user = authenticateUser(token);
		if (term == null) {
			logger.info("Invalid entity query (term is null).");
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		Query query = createQuery(domain, user);
		String typeDecoded = decodeBase64(type);
		
		logger.info("Querying entity '{}' of type {}.", term, typeDecoded);
		
		List<ResultItemRVO> result = performQuery(query, term, typeDecoded);
		return Response.ok(result).build();
	}
	
	// ----------------------------------------------------
	
	private List<ResultItemRVO> performQuery(Query query, String term, String type) {
		new TermSearcher().prepareQuery(query, term, Mode.ENTITY, type);

        return buildResult(query.getResult());
	}
	
}
