/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  A controller for widgets.
 * </p>
 *
 * <p>
 * 	Created Feb 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface WidgetController {
	
	/**
	 * Remove a widget.
	 * @param widget The widget to be removed.
	 */
	void removeWidget(WidgetSpec widget);

}
