/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.WDGT;

/**
 * <p>
 *  Implementation of {@link MenuItem}.
 * </p>
 *
 * <p>
 * 	Created Feb 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNMenuItem extends ResourceView implements MenuItem {

	
	/**
	 * Constructor for a new menu item.
	 */
	public SNMenuItem() {
		super();
	}

	/**
	 * Constructor wrapping an existing item node.
	 * @param resource
	 */
	public SNMenuItem(ResourceNode resource) {
		super(resource);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		return new SimpleResourceID(getQualifiedName());
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return stringValue(RB.HAS_NAME);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		setValue(RB.HAS_NAME, name);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Integer getPosition() {
		return intValue(Aras.HAS_SERIAL_NUMBER);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setPosition(Integer position) {
		setValue(Aras.HAS_SERIAL_NUMBER, position);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Perspective getPerspective() {
		SemanticNode node = SNOPS.fetchObject(this, WDGT.SHOWS_PERSPECTIVE);
		if (node != null && node.isResourceNode()) {
			return new SNPerspective(node.asResource());
		}
		return null;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setPerspective(Perspective perspective) {
		setValue(WDGT.SHOWS_PERSPECTIVE, perspective);
	}

}
