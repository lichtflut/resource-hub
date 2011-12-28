/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;

import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Loadable resetable model.
 * </p>
 *
 * <p>
 * 	Created Dec 6, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface LoadableModel<T> extends IModel<T>{

	/**
	 * Resets the model.
	 */
	void reset();
	
	T load(); 

}