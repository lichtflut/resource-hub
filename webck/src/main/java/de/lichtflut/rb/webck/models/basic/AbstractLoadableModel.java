/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;


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
	
	@Override
	public final void reset() {
		loaded = null;
	}
	
	@Override
	public final T getObject() {
		if (loaded == null) {
			loaded = load();
		}
		if (loaded != null) {
			return loaded;	
		} else {
			return getDefault();
		}
	}
	
	@Override
	public void setObject(final T loaded) {
		this.loaded = loaded;
	}
	
	@Override
	public void detach() {
	}
	
	public T getDefault() {
		return null;
	}
	
	// ----------------------------------------------------

	public abstract T load(); 
	
	
}