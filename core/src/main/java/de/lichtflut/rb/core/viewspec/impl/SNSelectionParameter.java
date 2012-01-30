/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

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
 *  [DESCRIPTION]
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
	 * 
	 */
	public SNSelectionParameter() {
		super();
	}

	/**
	 * @param resource
	 */
	public SNSelectionParameter(ResourceNode resource) {
		super(resource);
	}
	
	// ----------------------------------------------------
	
	public ResourceID getField() {
		return resource(singleObject(this, WDGT.CONCERNS_FIELD));
	}
	
	public SemanticNode getTerm() {
		return singleObject(this, WDGT.HAS_TERM);
	}
	
	public void setField(ResourceID id) {
		setValue(WDGT.CONCERNS_FIELD, id);
	}
	
	public void setTerm(SemanticNode term) {
		setValue(WDGT.HAS_TERM, term);
	}

	/**
	 * Unset any field constraint.
	 */
	public void unsetField() {
		SNOPS.remove(this, WDGT.CONCERNS_FIELD);
	}
	
}
