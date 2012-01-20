/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.common.TypedPanel;

/**
 * <p>
 *  Abstract widget panel.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class AbstractWidget extends TypedPanel<WidgetSpec> {

	/**
	 * @param id
	 */
	public AbstractWidget(String id, IModel<WidgetSpec> spec) {
		super(id, spec);
	}
	
}
