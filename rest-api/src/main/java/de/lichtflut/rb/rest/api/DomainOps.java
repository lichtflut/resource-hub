/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.rest.api.security.RBOperation;

/**
 * @author nbleisch
 *
 */
@Component
@Path("domain/{" + RBServiceEndpoint.DOMAIN_ID_PARAM + "}/")
public class DomainOps extends RBServiceEndpoint {

	@DELETE
	@RBOperation(type = TYPE.DOMAIN_DELETE)
	public Response deleteDomain(@PathParam(DOMAIN_ID_PARAM) String domainID, @QueryParam(AUTH_TOKEN) String token){
		RBUser user = authenticateUser(token);
		if(!getAuthHandler().isAuthorized(user, domainID)){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ServiceProvider provider = getProvider(domainID, user);
		return Response.ok().build();
		
	}
	
	@POST
	@RBOperation(type = TYPE.DOMAIN_CREATE)
	public Response createDomain(@PathParam(DOMAIN_ID_PARAM) String domainID, @QueryParam(AUTH_TOKEN) String token){
		RBUser user = authenticateUser(token);
		if(!getAuthHandler().isAuthorized(user, domainID)){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ServiceProvider provider = getProvider(domainID, user);
		return Response.ok().build();
		
	}
	
}
