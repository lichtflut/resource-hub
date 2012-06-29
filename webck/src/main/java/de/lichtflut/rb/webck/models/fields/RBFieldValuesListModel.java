/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.fields;

import de.lichtflut.rb.core.entity.RBField;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

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
public class RBFieldValuesListModel implements IModel<List<RBFieldValueModel<?>>> {

	private final IModel<RBField> model;
	
	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * @param model - model of {@link RBField}
	 */
	public RBFieldValuesListModel(final IModel<RBField> model){
		this.model = model;
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RBFieldValueModel<?>> getObject() {
		if (model == null || model.getObject() == null) {
			return null;
		} 
		final RBField rbField = model.getObject();
		final List<RBFieldValueModel<?>> result = new ArrayList<RBFieldValueModel<?>>(rbField.getSlots());
		for (int i=0; i < rbField.getSlots(); i++) {
			result.add(new RBFieldValueModel<Object>(rbField, i));
		}
		return result;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final List<RBFieldValueModel<?>> object) {
		throw new UnsupportedOperationException("Value may not be set.");
	}

	// -----------------------------------------------------
	
	@Override
	public void detach() {
		// Do nothing
	}

}
