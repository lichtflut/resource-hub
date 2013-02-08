/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.common;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 * This panel is the standard component for Glasost titles.
 * </p>
 * Created: Jan 16, 2013
 *
 * @author Ravi Knox
 */
public class PanelTitle extends Panel {

	/**
	 * Constructor.
	 * @param id Component id
	 * @param model Contains the title text.
	 */
	public PanelTitle(final String id, final IModel<String> model) {
		super(id, model);
		add(new Label("title", model));
	}

}
