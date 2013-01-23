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

import static de.lichtflut.rb.rest.api.config.LinkRelations.*;
import static de.lichtflut.rb.rest.api.config.ResourceTypes.*;
import static de.lichtflut.rb.rest.api.config.PathConstants.*;
import de.lichtflut.rb.rest.api.models.generate.SystemIdentity;
import de.lichtflut.rb.rest.api.models.transfer.HttpMethod;
import de.lichtflut.rb.rest.api.models.transfer.ResourceMeta;
import de.lichtflut.rb.rest.api.models.transfer.RestBaseModel;
import de.lichtflut.rb.rest.api.models.transfer.UserCredentials;



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
				rel(SELF_REL).
				build());
	
		
		UserCredentials dummyTemplateCredentials = new UserCredentials();
		dummyTemplateCredentials.setId("");
		dummyTemplateCredentials.setPassword("");
		rMeta.addLink(buildLink().
				href(getPath(AuthOps.class)).
				rel(CREATE_AUTH_TOKEN_REL).
				method(HttpMethod.POST).
				resourceType(TOKEN_SERVICE).
				body(dummyTemplateCredentials).
				build());
		
		rMeta.addLink(buildLink().
				href(getPath(EntitiyResource.class)).
				rel(GET_ENTITIES_REL).
				resourceType(ENTITIES_TYPE).
				build());

		rMeta.addLink(buildLink().
				templateRef(getPath(EntitiyResource.class) + ENTITY_PATH).
				rel(GET_ENTITY_REL).
				resourceType(ENTITY_TYPE).
				build());

		return Response.ok(outputEntity).build();
	}
	
	
	
}
