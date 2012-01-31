/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Model for unchecked casts.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CastingModel<T> extends AbstractReadOnlyModel<T> {

	private final IModel<?> model;
	
	// ----------------------------------------------------

	public CastingModel(IModel<?> model) {
		this.model = model;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() {
		return (T) model.getObject();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
		super.detach();
		model.detach();
	}

}
