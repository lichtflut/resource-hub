/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBField;

/**
 * 
 * <p>
 *  Model for the list of values of an RBField.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class RBFieldListModel implements IModel<List<RBFieldModel<?>>> {

	private final IModel<RBField> model;
	
	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * @param model - model of {@link RBField}
	 */
	public RBFieldListModel(final IModel<RBField> model){
		this.model = model;
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RBFieldModel<?>> getObject() {
		if (model == null || model.getObject() == null) {
			return null;
		} 
		final RBField rbField = model.getObject();
		final List<RBFieldModel<?>> result = new ArrayList<RBFieldModel<?>>(rbField.getSlots());
		for (int i=0; i < rbField.getSlots(); i++) {
			result.add(new RBFieldModel<Object>(rbField, i));
		}
		return result;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final List<RBFieldModel<?>> object) {
		throw new UnsupportedOperationException("Value may not be set.");
	}

	// -----------------------------------------------------
	
	@Override
	public void detach() {
		// Do nothing
	}

}
