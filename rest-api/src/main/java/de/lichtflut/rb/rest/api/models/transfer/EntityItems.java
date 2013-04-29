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
