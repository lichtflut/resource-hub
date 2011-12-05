/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import java.io.Serializable;

import org.arastreju.sge.model.ResourceID;

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
	 * @param id
	 * @param type
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
	
	// ----------------------------------------------------
	
	public boolean hasId() {
		return id != null;
	}
	
	public boolean hasType() {
		return type != null;
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
	
}
