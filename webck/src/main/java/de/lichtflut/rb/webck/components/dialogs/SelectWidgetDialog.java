/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  Modal dialog for selection of a widget.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class SelectWidgetDialog extends AbstractRBDialog {

	/**
	 * @param id
	 */
	public SelectWidgetDialog(String id) {
		super(id);
	}
	
	// ----------------------------------------------------
	
	protected abstract void onSelection(final WidgetSpec spec);
	
	

}
