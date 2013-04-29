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
package de.lichtflut.rb.core.entity;

import java.io.Serializable;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.infra.Infra;

/**
 * <p>
 *  Handle of an entity that needs not to exist now.
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityHandle implements Serializable {
	
	private ResourceID type;
	
	private ResourceID id;

    private boolean onCreation;

	// -- STATICS -----------------------------------------
	
	public static EntityHandle forID(ResourceID id) {
		return new EntityHandle().setId(id);
	}
	
	public static EntityHandle forType(ResourceID type) {
		return new EntityHandle().setType(type);
	}
	
	// ----------------------------------------------------

	public EntityHandle() {
	}
	
	/**
     * Constructor.
	 * @param id The id.
	 * @param type The type of the entity.
     *
	 */
	public EntityHandle(ResourceID id, ResourceID type) {
		this.id = id;
		this.type = type;
	}

	// ----------------------------------------------------
	
	public ResourceID getType() {
        return type;
	}
	
	public ResourceID getId() {
		return id;
	}
	
	public EntityHandle setType(ResourceID type) {
		this.type = type;
		return this;
	}
	
	public EntityHandle setId(ResourceID id) {
		this.id = id;
		return this;
	}

    public EntityHandle markOnCreation() {
        this.onCreation = true;
        return this;
    }

    public EntityHandle markPersisted() {
        this.onCreation = false;
        return this;
    }
	
	// ----------------------------------------------------
	
	public boolean hasId() {
		return id != null;
	}
	
	public boolean hasType() {
		return type != null;
	}

    public boolean isOnCreation() {
        return onCreation;
    }

    // ----------------------------------------------------
	
	@Override
	public String toString() {
		if (id != null) {
			return "" + id;	
		} else {
			return "type=" + type; 
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EntityHandle) {
			final EntityHandle other = (EntityHandle) obj;
            return Infra.equals(id, other.id) && Infra.equals(type, other.getType());
        }
		return super.equals(obj);
	}

}
