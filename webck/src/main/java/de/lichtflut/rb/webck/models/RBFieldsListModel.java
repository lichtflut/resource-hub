/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;

/**
 * 
 * <p>
 *  Model for the list of an entities fields.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class RBFieldsListModel implements IModel<List<RBField>> {

	private final IModel<RBEntity> model;
	
	// -----------------------------------------------------

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * @param model - model of {@link RBEntity}
	 */
	public RBFieldsListModel(final IModel<RBEntity> model){
		this.model = model;
	}
	
	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RBField> getObject() {
		if (model == null || model.getObject() == null) {
			return null;
		} 
		return model.getObject().getAllFields();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final List<RBField> object) {
		throw new UnsupportedOperationException("Value may not be set.");
	}

	// -----------------------------------------------------
	
	@Override
	public void detach() {
		// Do nothing
	}

}
