/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import static de.lichtflut.rb.rest.api.config.LinkRelations.SELF_REL;
import static de.lichtflut.rb.rest.api.config.LinkRelations.GET_ENTITY_REL;
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
public class EntitiyResource extends RBServiceEndpoint {

	public static final String RESOURCE_TYPE = "http://rb.lichtflut.de/types/entities";

	@Path("")
	@GET
	public Response getEntities(
			@PathParam(DOMAIN_ID_PARAM) final String domainID,
			@CookieParam(value = AuthModule.COOKIE_SESSION_AUTH) final String token) {
		RBUser user;
		try {
			user = authenticateUser(token);
		} catch (UnauthenticatedUserException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ServiceProvider provider = getProvider(domainID, user);
		EntityItems entityItems = new EntityItems();
		XRDLink selfLink = new XRDLink();
		selfLink.setHref(getPath(this.getClass()).replace(
				"{" + DOMAIN_ID_PARAM + "}", domainID));
		selfLink.setLinkRel(SELF_REL);
		selfLink.setResourceType(ResourceTypes.ENTITIES_TYPE);
		entityItems.getResourceMeta().addLink(selfLink);
		final List<SNClass> types = provider.getTypeManager().findAllTypes();
		for (SNClass type : types) {
			final List<ResourceNode> entities = findResourcesByType(
					provider.getConversation(), type);
			for (ResourceNode node : entities) {
				EntityManager entityManager = provider.getEntityManager();
				RBEntity rbEntity = entityManager.find(new SimpleResourceID(
						node.getQualifiedName().toURI()));
				if (rbEntity == null) {
					continue;
				}
				Entity entity = mapEntity(rbEntity, entityManager, true);
				XRDLink entitySelfLink = new XRDLink();
				entitySelfLink.setHref((getPath(this.getClass()).replace("{"
						+ DOMAIN_ID_PARAM + "}", domainID))
						+ ENTITY_PATH.replace("{base64EntityID}", new String(
								Base64.encode(node.toURI()))));
				entitySelfLink.setLinkRel(GET_ENTITY_REL);
				entitySelfLink.setResourceType(ResourceTypes.ENTITY_TYPE);
				entity.getResourceMeta().addLink(entitySelfLink);
				entityItems.addEntity(entity);
			}
		}
		return Response.ok().entity(entityItems).build();
	}

	@GET
	@Path(ENTITY_PATH)
	public Response getEntity(
			@PathParam(DOMAIN_ID_PARAM) final String domainID,
			@PathParam("base64EntityID") String base64EntityID,
			@CookieParam(value = AuthModule.COOKIE_SESSION_AUTH) final String token) {
		RBUser user;
		try {
			user = authenticateUser(token);
		} catch (UnauthenticatedUserException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ServiceProvider provider = getProvider(domainID, user);
		String entityID = Base64.base64Decode(base64EntityID);
		EntityManager entityManager = provider.getEntityManager();
		RBEntity rbEntity = entityManager.find(new SimpleResourceID(entityID));
		if (rbEntity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Entity entity = mapEntity(rbEntity, entityManager, true);
		XRDLink selfLink = new XRDLink();
		selfLink.setHref((getPath(this.getClass()).replace("{"
				+ DOMAIN_ID_PARAM + "}", domainID))
				+ ENTITY_PATH.replace("{base64EntityID}", base64EntityID));
		selfLink.setLinkRel(SELF_REL);
		selfLink.setResourceType(ResourceTypes.ENTITY_TYPE);
		entity.getResourceMeta().addLink(selfLink);

		return Response.ok().entity(entity).build();
	}

	/**
	 * 
	 * @param rbEntity
	 * @param entityManager
	 *            - to determine if the association's object is an entity or not
	 * @return
	 */
	private Entity mapEntity(RBEntity rbEntity, EntityManager entityManager,
			boolean expandAssociations) {
		Entity entity = new Entity();
		entity.setId(rbEntity.getNode().toURI());
		if (expandAssociations) {
			for (Statement stmt : rbEntity.getNode().getAssociations()) {
				Association assoc = new Association();
				assoc.setEntityAssoc(false);
				SemanticNode object = stmt.getObject();
				if (object.isResourceNode()) {
					RBEntity e = entityManager.find(object.asResource());
					if (e != null && e.hasSchema()) {
						assoc.setEntityAssoc(true);
					}
				}
				assoc.setPredicate(stmt.getPredicate().toURI());
				assoc.setObject(stmt.getObject().toString());
				entity.getAssociations().add(assoc);
			}
		}
		return entity;
	}

}
