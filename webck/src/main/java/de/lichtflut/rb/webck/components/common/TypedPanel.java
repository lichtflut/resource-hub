/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.common;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Extension of {@link Panel} with a typed model.
 * </p>
 *
 * <p>
 * 	Created Jan 3, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class TypedPanel<T> extends Panel {

	/**
	 * @param id
	 * @param model
	 */
	public TypedPanel(String id, IModel<T> model) {
		super(id, model);
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return The typed model of this panel.
	 */
	@SuppressWarnings("unchecked")
	public IModel<T> getModel() {
		return (IModel<T>) getDefaultModel();
	}
	
	/**
	 * @return The typed model object.
	 */
	public T getModelObject() {
		return getModel().getObject();
	}
	
}
