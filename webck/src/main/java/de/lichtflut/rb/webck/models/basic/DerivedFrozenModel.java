/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;

import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Model derived from another model, that keeps the object until "reset" is called. 
 * </p>
 *
 * <p>
 * 	Created Feb 28, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class DerivedFrozenModel<T, M> extends AbstractLoadableModel<T> {

	private final IModel<M> originalModel;
	
	private final M original;
	
	// ----------------------------------------------------
	
	/**
	 * @param original
	 */
	public DerivedFrozenModel(IModel<M> original) {
		this.originalModel = original;
		this.original = null;
	}
	
	/**
	 * @param original
	 */
	public DerivedFrozenModel(M original) {
		this.original = original;
		this.originalModel = null;
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public T load() {
		if (original != null) {
			return derive(original);
		} else if (originalModel != null && originalModel.getObject() != null) {
			return derive(originalModel.getObject());
		} else {
			return getDefault();
		}
	}
	
	public T getDefault() {
		return null;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {
		if (originalModel != null) {
			originalModel.detach();
		}
	}
	
	// ----------------------------------------------------
	
	/**
	 * Derive the value from the original. The original will never be null.
	 * @param original The original model value.
	 * @return The derived value.
	 */
	protected abstract T derive(M original);
	
	// ----------------------------------------------------
	
	protected M getOriginal() {
		if (original != null) {
			return original;
		} else if (originalModel != null) {
			return originalModel.getObject();
		} else {
			return null;
		}
	}
	
}
