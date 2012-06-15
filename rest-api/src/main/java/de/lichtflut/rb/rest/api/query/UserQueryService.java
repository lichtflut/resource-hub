/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.query;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import de.lichtflut.rb.rest.api.RBServiceEndpoint;

/**
 * <p>
 *  Resource for querying of users.
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@Component
@Path("query/users")
public class UserQueryService extends RBServiceEndpoint {
		
	public UserQueryService(){}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response search(@QueryParam(value="term") String term){
		return Response.ok(new ResultItemRVO(), MediaType.APPLICATION_JSON).build();
	}
	
}
