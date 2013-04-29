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
