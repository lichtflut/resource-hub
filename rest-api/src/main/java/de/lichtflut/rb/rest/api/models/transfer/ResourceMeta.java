/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.models.transfer;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 *  Contains resource meta attributes like links, types and so on
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 16, 2013
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourceMeta {
		
	private Collection<Link> links = new ArrayList<Link>();

	public void addLink(Link link){
		links.add(link);
	}
	
	public Collection<Link> getLinks(){
		return links;
		//return new ArrayList<Link>(links);
	}
	
}
