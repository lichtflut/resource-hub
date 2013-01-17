/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.models.transfer.builder;

import de.lichtflut.rb.rest.api.models.transfer.HttpMethod;
import de.lichtflut.rb.rest.api.models.transfer.Link;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 16, 2013
 */
public class LinkBuilder {

	Link link = new Link();
	
	public static LinkBuilder buildLink(){
		return new LinkBuilder();
	}
	
	
	public LinkBuilder href(String href){
		link.setHref(href);
		return this;
	}
	
	public LinkBuilder rel(String linkRel){
		link.setLinkRel(linkRel);
		return this;
	}
	
	public LinkBuilder method(HttpMethod method){
		link.setMethod(method);
		return this;
	}
	
	public LinkBuilder resourceType(String resourceType){
		link.setResourceType(resourceType);
		return this;
	}
	
	public Link build(){
		return link;
	}
	
	
}
