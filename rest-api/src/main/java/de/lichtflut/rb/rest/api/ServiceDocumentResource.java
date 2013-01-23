/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import static de.lichtflut.rb.rest.api.config.LinkRelations.CREATE_AUTH_TOKEN_REL;
import static de.lichtflut.rb.rest.api.config.LinkRelations.GET_DOMAINS_REL;
import static de.lichtflut.rb.rest.api.config.LinkRelations.GET_ENTITIES_REL;
import static de.lichtflut.rb.rest.api.config.LinkRelations.GET_ENTITY_REL;
import static de.lichtflut.rb.rest.api.config.LinkRelations.SELF_REL;
import static de.lichtflut.rb.rest.api.config.PathConstants.ENTITY_PATH;
import static de.lichtflut.rb.rest.api.config.ResourceTypes.DOMAINS_TYPE;
import static de.lichtflut.rb.rest.api.config.ResourceTypes.ENTITIES_TYPE;
import static de.lichtflut.rb.rest.api.config.ResourceTypes.ENTITY_TYPE;
import static de.lichtflut.rb.rest.api.config.ResourceTypes.TOKEN_SERVICE;
import static de.lichtflut.rb.rest.api.models.transfer.builder.LinkBuilder.buildLink;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response getServiceDocument() throws UnsupportedEncodingException{
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
				templateRef(URLDecoder.decode(getPath(EntitiyResource.class), "UTF-8")).
				rel(GET_ENTITIES_REL).
				resourceType(ENTITIES_TYPE).
				build());

		rMeta.addLink(buildLink().
				templateRef(URLDecoder.decode(getPath(EntitiyResource.class) + ENTITY_PATH, "UTF-8")).
				rel(GET_ENTITY_REL).
				resourceType(ENTITY_TYPE).
				build());

		rMeta.addLink(buildLink().
				href(getPath(DomainResource.class)).
				rel(GET_DOMAINS_REL).
				resourceType(DOMAINS_TYPE).
				build());

		
		return Response.ok(outputEntity).build();
	}
	
	
	
}
