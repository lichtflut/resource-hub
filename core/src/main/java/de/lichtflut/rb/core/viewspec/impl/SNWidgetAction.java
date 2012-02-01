/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.viewspec.WidgetAction;

/**
 * <p>
 *  Action supported by a widget.
 * </p>
 *
 * <p>
 * 	Created Feb 1, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNWidgetAction extends ResourceView implements WidgetAction {

	/**
	 * Constructor for new actions.
	 */
	public SNWidgetAction() {
		super();
	}

	/**
	 * @param resource
	 */
	public SNWidgetAction(ResourceNode resource) {
		super(resource);
	}

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getActionType() {
		final SemanticNode type = SNOPS.fetchObject(this, RDF.TYPE);
		if (type != null && type.isResourceNode()) {
			return type.asResource();
		} else {
			return null;
		}
	}
}
