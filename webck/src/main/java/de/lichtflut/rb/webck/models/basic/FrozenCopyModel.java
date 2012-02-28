/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;

import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Model that keeps a copy of a another model, that may be a loadable detachable model.
 * </p>
 *
 * <p>
 * 	Created Feb 28, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class FrozenCopyModel<T> extends DerivedFrozenModel<T, T> {

	/**
	 * @param original
	 */
	public FrozenCopyModel(IModel<T> original) {
		super(original);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected T derive(T original) {
		return original;
	}

}
