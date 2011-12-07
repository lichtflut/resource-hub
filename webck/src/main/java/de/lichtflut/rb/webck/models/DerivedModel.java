/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.IModel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Dec 6, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class DerivedModel<T> implements IModel<T> {

	private final IModel<T> original;
	
	private T value;
	
	// ----------------------------------------------------
	
	/**
	 * @param original
	 */
	public DerivedModel(IModel<T> original) {
		this.original = original;
	}
	
	// ----------------------------------------------------

	public T getObject() {
		if (value == null) {
			value = derive(original);
		}
		return value;
	}

	public void setObject(T value) {
		this.value = value;
	}
	
	public void detach() {
	}
	
	public void reset() {
		value = null;
	}
	
	// ----------------------------------------------------
	
	protected T derive(IModel<T> original) {
		return original.getObject();
	}
	
}
