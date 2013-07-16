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

import static de.lichtflut.rb.rest.api.config.LinkRelations.SELF_REL;
import static de.lichtflut.rb.rest.api.config.PathConstants.ENTITIES_BASE_PATH;
import static de.lichtflut.rb.rest.api.config.PathConstants.ENTITY_PATH;

import java.util.List;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.resource.Singleton;

import de.lichtflut.rb.core.eh.UnauthenticatedUserException;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.rest.api.config.ResourceTypes;
import de.lichtflut.rb.rest.api.models.transfer.Association;
import de.lichtflut.rb.rest.api.models.transfer.Entity;
import de.lichtflut.rb.rest.api.models.transfer.EntityItems;
import de.lichtflut.rb.rest.api.models.transfer.XRDLink;
import de.lichtflut.rb.rest.delegate.providers.ServiceProvider;

/**
 * <p>
 * TODO: To document
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 10, 2013
 */
@Component
@Singleton
@Path(ENTITIES_BASE_PATH)
@Produces({ MediaType.APPLICATION_JSON })
public class EntityResource extends RBServiceEndpoint {

	@GET
	public Response getEntities(@CookieParam(value=AuthModule.COOKIE_SESSION_AUTH) final String token) {
		RBUser user;
		try {
			user = authenticateUser(token);
		} catch (UnauthenticatedUserException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ServiceProvider provider = getProvider("root", user);
		EntityItems entityItems = new EntityItems();
		XRDLink selfLink = new XRDLink();
		selfLink.setHref(getPath(this.getClass()));
		selfLink.setLinkRel(SELF_REL);
		selfLink.setResourceType(ResourceTypes.ENTITIES_TYPE);
		entityItems.getResourceMeta().addLink(selfLink);
		final List<SNClass> types = provider.getTypeManager().findAllTypes();
		for (SNClass type : types) {
			final List<ResourceNode> entities = findResourcesByType(provider.getConversation(), type);
			for (ResourceNode entity : entities) {
				String encodedID = new String(Base64.encode(entity
						.getQualifiedName().toURI()));
				Response entityRsp = getEntity(encodedID, token);
				if(entityRsp.getStatus() != Status.OK.getStatusCode()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
				if (entityRsp.getStatus() != 200) {
					continue;
				}
				entityItems.addEntity((Entity) entityRsp.getEntity());
			}
		}
		return Response.ok().entity(entityItems).build();
	}

	@GET
	@Path(ENTITY_PATH)
	public Response getEntity(@PathParam("base64EntityID") String base64EntityID,
			@CookieParam(value=AuthModule.COOKIE_SESSION_AUTH) final String token) {
		RBUser user;
		try {
			user = authenticateUser(token);
		} catch (UnauthenticatedUserException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ServiceProvider provider = getProvider("root", user);
		String entityID = Base64.base64Decode(base64EntityID);
		EntityManager entityManager = provider.getEntityManager();
		RBEntity rbEntity = entityManager.find(new SimpleResourceID(entityID));
		if (rbEntity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Entity entity = mapEntity(rbEntity, entityManager);

		return Response.ok().entity(entity).build();
	}

	private Entity mapEntity(RBEntity rbEntity, EntityManager entityManager) {
		String base64EntityID = new String(Base64.encode(rbEntity.getID()
				.toURI()));

		Entity entity = new Entity();
		entity.setId(rbEntity.getNode().toURI());
		XRDLink selfLink = new XRDLink();
		selfLink.setHref(getPath(this.getClass())
				+ ENTITY_PATH.replace("{base64EntityID}", base64EntityID));
		selfLink.setLinkRel(SELF_REL);
		selfLink.setResourceType(ResourceTypes.ENTITY_TYPE);
		entity.getResourceMeta().addLink(selfLink);

		for (Statement stmt : rbEntity.getNode().getAssociations()) {
			Association assoc = new Association();
			assoc.setEntityAssoc(false);
			SemanticNode object = stmt.getObject();
			if (object.isResourceNode()) {
				RBEntity e = entityManager.find(object.asResource());
				if(e !=null && e.hasSchema()){
					assoc.setEntityAssoc(true);
				}
			}
			assoc.setPredicate(stmt.getPredicate().toURI());
			assoc.setObject(stmt.getObject().toString());
			entity.getAssociations().add(assoc);
		}
		return entity;
	}

}
