/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.widgets.EntityListWidget;

/**
 * <p>
 *  Configuration panel of a {@link EntityListWidget}.
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityListWidgetConfigPanel extends AbstractWidgetConfigPanel {
	
	/**
	 * @param id
	 * @param model
	 */
	public EntityListWidgetConfigPanel(String id, IModel<WidgetSpec> model) {
		super(id, model);
		
	}

}
