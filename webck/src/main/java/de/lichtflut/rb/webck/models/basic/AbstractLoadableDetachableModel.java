/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;


/**
 * <p>
 *  Model that loads it data and holds it until detach phase or explicitly reseted.
 * </p>
 *
 * <p>
 * 	Created Dec 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractLoadableDetachableModel<T> extends AbstractLoadableModel<T> {
	
	@Override
	public void detach() {
		reset();
	}
	
}