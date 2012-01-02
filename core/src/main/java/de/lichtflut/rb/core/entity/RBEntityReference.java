/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import java.util.Locale;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.ValueNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.persistence.ResourceResolver;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;

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
	
	private ResourceNode resource;
	
	private ResourceResolver resourceResolver;
	
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
	 */
	public RBEntityReference(final ResourceNode resource) {
		this.id = new SimpleResourceID(resource);
		this.resource = resource;
	}

	// ----------------------------------------------------
	
	public void setResource(final ResourceNode resource) {
		this.resource = resource;
	}
	
	public void setResolver(final ResourceResolver resolver) {
		this.resourceResolver = resolver;
	}
	
	// ----------------------------------------------------
	
	public ResourceID getId() {
		return id;
	}

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
		if (resource != null) {
			return resource;
		}
		return id.asResource();
	}

	public ValueNode asValue() {
		return id.asValue();
	}
	
	// ----------------------------------------------------
	
	public String getLabel(Locale locale) {
		final ResourceNode resolved = getResolvedResource();
		if (resolved != null) {
			return ResourceLabelBuilder.getInstance().getLabel(resolved, locale);
		} else {
			return ResourceLabelBuilder.getInstance().getLabel(id, locale);
		}
	}
	
	@Override
	public String toString() {
		return getLabel(Locale.getDefault());
	}
	
	// ----------------------------------------------------
	
	private ResourceNode getResolvedResource() {
		if (resource == null && resourceResolver != null) {
			return resourceResolver.resolve(id);
		}
		return resource;
	}
	
}
