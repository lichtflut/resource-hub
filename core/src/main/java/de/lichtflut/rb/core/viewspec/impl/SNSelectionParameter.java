/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import static org.arastreju.sge.SNOPS.fetchObject;
import static org.arastreju.sge.SNOPS.resource;
import static org.arastreju.sge.SNOPS.singleObject;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.viewspec.WDGT;

/**
 * <p>
 *  Decorator for a resource node representing a parameter of a selection.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNSelectionParameter extends ResourceView {

	/**
	 * Constructor for new parameters.
	 */
	public SNSelectionParameter() {
		super();
	}

	/**
	 * Constructor for existing parameters.
	 * @param resource The existing resource representing the parameter.
	 */
	public SNSelectionParameter(ResourceNode resource) {
		super(resource);
	}
	
	// ----------------------------------------------------
	
	public ResourceID getField() {
		return resource(singleObject(this, WDGT.CONCERNS_FIELD));
	}
	
	public void setField(ResourceID id) {
		setValue(WDGT.CONCERNS_FIELD, id);
	}
	
	/**
	 * Unset any field constraint.
	 */
	public void unsetField() {
		SNOPS.remove(this, WDGT.CONCERNS_FIELD);
	}
	
	// ----------------------------------------------------
	
	public SemanticNode getTerm() {
		return fetchObject(this, WDGT.HAS_TERM);
	}
	
	public void setTerm(SemanticNode term) {
		setValue(WDGT.HAS_TERM, term);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Param(");
		final ResourceID field = getField();
		if (field != null) {
			sb.append(field.getQualifiedName() + "=");
		}
		sb.append(getTerm());
		sb.append(")");
		return sb.toString();
	}
	
}
