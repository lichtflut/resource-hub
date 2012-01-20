/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  Simple implementation of {@link WidgetSpec} interface for detached use.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SimpleWidgetSpec implements WidgetSpec {

	private Selection selection;

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		return null;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return null;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return null;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return null;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Selection getSelection() {
		return selection;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setName(String name) {
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle(String title) {
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setDescription(String desc) {
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setSelection(Selection selection) {
		this.selection = selection;
	}

}
