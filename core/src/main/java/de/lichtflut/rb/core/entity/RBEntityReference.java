/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.ValueNode;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  This is a reference to another entity. 
 *  Either this reference is resolved and provides an {@link RBEntity}. 
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBEntityReference implements ResourceID {
	
	private final ResourceID id;
	
	private RBEntity entity;
	
	private transient ReferenceResolver resolver;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The id.
	 */
	public RBEntityReference(final ResourceID id) {
		this.id = id;
	}
	
	/**
	 * Constructor.
	 * @param id The id.
	 * @param resolver The resolver for the entity.
	 */
	public RBEntityReference(final ResourceID id, final ReferenceResolver resolver) {
		this.id = id;
		this.resolver = resolver;
	}

	/**
	 * Constructor.
	 * @param id The id.
	 * @param entity The resolved entity.
	 */
	public RBEntityReference(final RBEntity entity) {
		this.id = entity.getID();
		this.entity = entity;
	}

	// ----------------------------------------------------
	
	public RBEntity getEntity() {
		if (entity == null && resolver != null) {
			resolver.resolve(this);
		}
		return entity;
	}
	
	public boolean isResolved() {
		return getEntity() != null;
	}
	
	public void setEntity(final RBEntity entity) {
		this.entity = entity;
	}
	
	public void setResolver(final ReferenceResolver resolver) {
		this.resolver = resolver;
	}
	
	// ----------------------------------------------------

	public boolean isValueNode() {
		return id.isValueNode();
	}

	public boolean isResourceNode() {
		return id.isResourceNode();
	}

	public QualifiedName getQualifiedName() {
		return id.getQualifiedName();
	}

	public ResourceNode asResource() {
		return id.asResource();
	}

	public boolean references(ResourceID ref) {
		return id.references(ref);
	}

	public ValueNode asValue() {
		return id.asValue();
	}
	
	// ----------------------------------------------------
	
	@Override
	public String toString() {
		if (isResolved()) {
			return entity.getLabel();
		} else {
			return id.getQualifiedName().toURI();
		}
	}
	
}
