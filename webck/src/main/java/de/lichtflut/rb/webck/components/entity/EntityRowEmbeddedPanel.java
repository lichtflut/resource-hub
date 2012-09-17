/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBField;

/**
 * <p>
 *  Panel containing the fields of an embedded entity.
 * </p>
 *
 * <p>
 * 	Created Aug 22, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityRowEmbeddedPanel extends Panel {

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model containing the field with the embedded entity.
	 */
	public EntityRowEmbeddedPanel(final String id, final IModel<RBField> model) {
		super(id, model);


	}

}
