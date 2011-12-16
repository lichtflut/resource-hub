/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Model derived from another model.
 * </p>
 *
 * <p>
 * 	Created Dec 6, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class DerivedModel<T, M> extends AbstractReadOnlyModel<T> {

	private final IModel<M> originalModel;
	
	private final M original;
	
	// ----------------------------------------------------
	
	/**
	 * @param original
	 */
	public DerivedModel(IModel<M> original) {
		this.originalModel = original;
		this.original = null;
	}
	
	/**
	 * @param original
	 */
	public DerivedModel(M original) {
		this.original = original;
		this.originalModel = null;
	}
	
	// ----------------------------------------------------

	public T getObject() {
		if (original != null) {
			return derive(original);
		} else if (originalModel != null) {
			return derive(originalModel.getObject());
		} else {
			return null;
		}
	}

	// ----------------------------------------------------
	
	protected abstract T derive(M original);
	
}
