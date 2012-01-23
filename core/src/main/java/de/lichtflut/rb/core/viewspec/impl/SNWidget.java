/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  Node representing a widget specification.
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNWidget extends ResourceView implements WidgetSpec {

	/**
	 * Default constructor for new widget specifications.
	 */
	public SNWidget() {
	}
	
	/**
	 * @param resource
	 */
	public SNWidget(ResourceNode resource) {
		super(resource);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		return this;
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
	public String getTitle() {
		return stringValue(RB.HAS_NAME);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return stringValue(RB.HAS_DESCRIPTION);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setDescription(String desc) {
		// TODO Auto-generated method stub
		
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setSelection(Selection selection) {
		// TODO Auto-generated method stub
		
	}
	
	// ----------------------------------------------------
	
	private String stringValue(ResourceID attribute) {
		return SNOPS.string(SNOPS.fetchObject(this, attribute));
	}

}
