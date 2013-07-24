/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
public abstract class SelectWidgetDialog extends RBDialog {

	/**
     * Constructor.
	 * @param id The wicket ID.
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
