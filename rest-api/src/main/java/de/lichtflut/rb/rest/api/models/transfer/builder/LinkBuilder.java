/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.models.transfer.builder;

import de.lichtflut.rb.rest.api.models.transfer.HttpMethod;
import de.lichtflut.rb.rest.api.models.transfer.XRDLink;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 16, 2013
 */
public class LinkBuilder {

	XRDLink link = new XRDLink();
	
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
	
	public LinkBuilder body(Object body){
		link.setBodyProperty(body);
		return this;
	}
	
	public LinkBuilder property(String key, String value){
		link.getProperties().put(key, value);
		return this;
	}
	
	public LinkBuilder templateRef(String tempRef){
		link.setTemplateRel(tempRef);
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
	
	public XRDLink build(){
		return link;
	}
	
	
}
