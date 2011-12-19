/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;


/**
 * <p>
 *  Model that derives it data from another model. Similar to {@link LoadableModel} but
 *  the data will never be cached.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractDerivedModel<T, S> extends AbstractReadOnlyModel<T> {
	
	private final IModel<S> source;
	
	// ----------------------------------------------------
	
	/**
	 * @param source
	 */
	public AbstractDerivedModel(IModel<S> source) {
		this.source = source;
	}
	
	// ----------------------------------------------------

	public abstract T derive(IModel<S> source); 
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public final T getObject() {
		return derive(source);
	}
	
}