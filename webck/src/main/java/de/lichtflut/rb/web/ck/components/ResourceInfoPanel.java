/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import de.lichtflut.rb.core.entity.RBEntity;

/**
 * This Panel represents an {@link RBEntity}.
 * The following Attributes will be displayed
 * <ul>
 * <li>Label</li>
 * <li>Image</li>
 * <li>Short description</li>
 * </ul>
 *
 * If none of these attributes can be attained through {@link RBEntity}s standard methods,
 * a {@link String} representation of the {@link RBEntity} will be displayed.
 *
 * Created: Aug 31, 2011
 *
 * @author Ravi Knox
 */
public class ResourceInfoPanel extends Panel {

	private static final long serialVersionUID = 1L;

	/**
	 * @param id - wicket:id
	 * @param entity - {@link RBEntity} which is to be displayed
	 */
	public ResourceInfoPanel(final String id, final RBEntity entity) {
		super(id);
		String title = entity.getLabel();
		if(title.equals("")){
			title = "Create new " + entity.getType();
		}
		add(new Label("label", title));
	}

}