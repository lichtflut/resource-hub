/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import static de.lichtflut.rb.rest.api.config.LinkRelations.SELF_REL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.rest.api.config.PathConstants;
import  static de.lichtflut.rb.rest.api.config.ResourceTypes.*;
import de.lichtflut.rb.rest.api.models.transfer.Domain;
import de.lichtflut.rb.rest.api.models.transfer.DomainItems;
import de.lichtflut.rb.rest.api.models.transfer.XRDLink;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 23, 2013
 */
@Component
@Path(PathConstants.DOMAINS_BASE_PATH)
public class DomainResource extends RBServiceEndpoint{

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllDomains(){
		DomainManager domainManager = authModule.getDomainManager();
		DomainItems items = new DomainItems();
		XRDLink selfLink = new XRDLink();
		selfLink.setHref(getPath(this.getClass()));
		selfLink.setLinkRel(SELF_REL);
		selfLink.setResourceType(DOMAINS_TYPE);
		for(RBDomain rbDomain  : domainManager.getAllDomains()){
			Domain domain = new Domain();
			domain.setDescription(rbDomain.getDescription());
			domain.setName(rbDomain.getName());
			domain.setTitle(rbDomain.getTitle());
			items.getDomains().add(domain);
		}
		return Response.ok(items).build();
	}
	
	
	
}
