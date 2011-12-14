/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;


/**
 * <p>
 *  Model that loads it data and holds it until explicitly reseted.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractLoadableModel<T> implements LoadableModel<T> {
	
	private T loaded;
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void reset() {
		loaded = null;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public final T getObject() {
		if (loaded == null) {
			loaded = load();
		}
		return loaded;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final T loaded) {
		this.loaded = loaded;
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void detach() {
	}
	
	// ----------------------------------------------------

	public abstract T load(); 
	
	
}