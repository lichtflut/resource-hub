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
		
	private Collection<XRDLink> links = new ArrayList<XRDLink>();

	public void addLink(XRDLink link){
		links.add(link);
	}
	
	public Collection<XRDLink> getLinks(){
		return links;
		//return new ArrayList<Link>(links);
	}
	
}
