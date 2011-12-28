/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.basic;

import java.util.List;

import org.apache.wicket.model.IModel;


/**
 * <p>
 *  Model that derives it data from another model. Similar to {@link LoadableModel} but
 *  the data will never be cached.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractDerivedListModel<T, S> extends AbstractDerivedModel<List<T>, S> {
	
	/**
	 * @param source
	 */
	public AbstractDerivedListModel(IModel<S> source) {
		super(source);
	}
	
}