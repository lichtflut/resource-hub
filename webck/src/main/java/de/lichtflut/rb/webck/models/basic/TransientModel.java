/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;

import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Model for transient objects.
 * </p>
 *
 * <p>
 * 	Created Dec 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TransientModel<T> implements IModel<T> {

	private transient T object;
	
	// ----------------------------------------------------
	
	/**
	 * Default constructor.
	 */
	public TransientModel() {
	}
	
	/**
	 * @param object
	 */
	public TransientModel(T object) {
		this.object = object;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public T getObject() {
		return object;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void setObject(T object) {
		this.object = object;
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void detach() {
		this.object = null;
	}

}
