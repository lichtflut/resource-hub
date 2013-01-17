/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import static de.lichtflut.rb.rest.api.models.transfer.builder.LinkBuilder.buildLink;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import de.lichtflut.rb.rest.api.models.transfer.ResourceMeta;
import de.lichtflut.rb.rest.api.models.transfer.RestBaseModel;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 16, 2013
 */
@Path("/")
public class ServiceDocumentResource extends RBServiceEndpoint{
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getServiceDocument(){
		RestBaseModel outputEntity = new RestBaseModel(){};
		ResourceMeta rMeta = outputEntity.getResourceMeta();
		rMeta.addLink(buildLink().
				href(getSelfReference()).
				rel("self").
				build());
		
		rMeta.addLink(buildLink().
				href(getPath(EntitiyResource.class)).
				rel("entites").
				build());
		
		
		return Response.ok(outputEntity).build();
	}
	
	
	
}
