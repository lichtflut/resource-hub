/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.widgets.catalog.WidgetCatalogPanel;
import org.apache.wicket.model.ResourceModel;

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
		
		setTitle(new ResourceModel("dialog.title"));
		
		add(new WidgetCatalogPanel(CONTENT) {
			@Override
			public void onSelection(WidgetSpec selected) {
				close(RBAjaxTarget.getAjaxTarget());
				SelectWidgetDialog.this.onSelection(selected);
			}
		});
		
		setWidth(600);
	}
	
	// ----------------------------------------------------
	
	protected abstract void onSelection(final WidgetSpec spec);

}
