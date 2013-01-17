/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import static de.lichtflut.rb.rest.api.config.PathConstants.ENTITIES_BASE_PATH;
import static de.lichtflut.rb.rest.api.config.PathConstants.ENTITY_PATH;
import static de.lichtflut.rb.rest.api.config.LinkRelations.*;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.resource.Singleton;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.rest.api.models.transfer.Entity;
import de.lichtflut.rb.rest.api.models.transfer.EntityItems;
import de.lichtflut.rb.rest.api.models.transfer.Link;
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
	
	@Path("")
	@GET
	public Response getEntities() {
		ServiceProvider provider = getProvider("root", null);
		EntityItems entityItems = new EntityItems();
		Link selfLink = new Link();
		selfLink.setHref(getPath(this.getClass()));
		selfLink.setLinkRel(SELF_REL);
		entityItems.getResourceMeta().addLink(selfLink);
		final List<SNClass> types = provider.getTypeManager().findAllTypes();
		for (SNClass type : types) {
			final List<ResourceNode> entities = findResourcesByType(provider.getConversation(), type);
			for (ResourceNode entity : entities) {
				String encodedID = new String(Base64.encode(entity.getQualifiedName().toURI()));
				Response entityRsp = getEntity(encodedID);
				if(entityRsp.getStatus()!=200){
					continue;
				}
				entityItems.addEntity((Entity) entityRsp.getEntity());
			}
		}
		return Response.ok().entity(entityItems).build();
	}
	
	@GET
	@Path(ENTITY_PATH)
	public Response getEntity(@PathParam("base64EntityID") String base64EntityID) {
		ServiceProvider provider = getProvider("root", null);
		String entityID = Base64.base64Decode(base64EntityID);
		EntityManager entityManager = provider.getEntityManager();
		RBEntity entity = entityManager.find(new SimpleResourceID(entityID));
		if(entity==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		Entity node = new Entity();
		node.setId(entity.getNode().toURI());
		Link selfLink = new Link();
		selfLink.setHref(getPath(this.getClass()) + ENTITY_PATH.replace("{base64EntityID}",base64EntityID));
		selfLink.setLinkRel(SELF_REL);
		node.getResourceMeta().addLink(selfLink);
		for(Statement stmt : entity.getNode().getAssociations()){
			node.addAssociation(stmt.getPredicate().toURI(), stmt.getObject().toString());
		}
		return Response.ok().entity(node).build();
	}
}
