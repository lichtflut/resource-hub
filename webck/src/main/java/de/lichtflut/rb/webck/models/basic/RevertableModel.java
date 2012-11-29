/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.wicket.model.IModel;


/**
 * <p>
 *  IModel that can be reverted. Therefore it keeps a clone.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RevertableModel<T> implements IModel<T> {
	
	private IModel<T> original;
	private IModel<T> clone;
	
	// ----------------------------------------------------
	
	/**
	 * @param original
	 */
	public RevertableModel(final IModel<T> original) {
		this.original = original;
		revert();
	}
	
	// ----------------------------------------------------

	public void revert() {
		clone = SerializationUtils.clone(original);
	}
	
	// ----------------------------------------------------
	
	@Override
	public T getObject() {
		return clone.getObject();
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void setObject(T object) {
		clone.setObject(object);
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void detach() {
		original.detach();
		clone.detach();
	}

}
