/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.models.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 *  This is the base model of all transfer models. introducing resource meta attributes
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 16, 2013
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class RestBaseModel {

	private ResourceMeta resourceMeta = new ResourceMeta();;

	/**
	 * @return the resourceMeta
	 */
	public ResourceMeta getResourceMeta() {
		return resourceMeta;
	}
	
}
