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
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 10, 2013
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Entity extends RestBaseModel{

	/**
	 * @return the associations
	 */
	public Collection<Association> getAssociations() {
		return associations;
	}

	public void addAssociation(String predicate, String object){
		Association assoc = new Association();
		assoc.setObject(object);
		assoc.setPredicate(predicate);
		associations.add(assoc);
	}
	
	private String id;
	private Collection<Association> associations = new ArrayList<Association>();

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
