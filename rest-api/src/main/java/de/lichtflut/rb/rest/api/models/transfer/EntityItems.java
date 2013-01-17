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
 * @created Jan 17, 2013
 */
@XmlRootElement(name="entities")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityItems extends RestBaseModel{

	private Collection<Entity> entities = new ArrayList<Entity>();

	/**
	 * @return the entities
	 */
	public Collection<Entity> getEntities() {
		return entities;
	}

	public void addEntity(Entity e){
		this.entities.add(e);
	}
	
	/**
	 * @param entities the entities to set
	 */
	public void setEntities(Collection<Entity> entities) {
		this.entities = entities;
	}
	
	
	
}
