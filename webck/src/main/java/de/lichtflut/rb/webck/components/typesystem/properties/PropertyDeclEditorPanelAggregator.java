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
package de.lichtflut.rb.webck.components.typesystem.properties;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.CreatePropertyDialog;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemHelpPanel;
import de.lichtflut.rb.webck.models.types.SNPropertyListModel;
import de.lichtflut.rb.webck.models.types.SNPropertyModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.arastreju.sge.model.nodes.views.SNProperty;

/**
 * <p>
 * Aggregator Panel for all operations on a {@link PropertyDeclaration}.
 * </p>
 * <p>
 * Created: Apr 23, 2012
 * </p>
 * 
 * @author Ravi Knox
 */
public class PropertyDeclEditorPanelAggregator extends Panel {

	/**
	 * Constructor.
	 * 
	 * @param id - wicket:id
	 */
	public PropertyDeclEditorPanelAggregator(String id) {
		super(id);
		addComponents();
	}

	// ------------------------------------------------------

	private void addComponents() {
		add(new TypeSystemHelpPanel("editor").setOutputMarkupId(true));
//		add(new Label("searchbox", "Search..."));
		add(new SNPropertyBrowserPanel("propertyBrowser", new SNPropertyListModel()) {
			@Override
			public void onPropertySelected(final SNProperty property, final AjaxRequestTarget target) {
				displayPropertyEditor(property);
			}

			@Override
			public void onCreateProperty(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreatePropertyDialog(hoster.getDialogID()));
			}
		});
	}

	protected void displayPropertyEditor(final SNProperty property) {
		final SNPropertyEditorPanel editor = new SNPropertyEditorPanel("editor", new SNPropertyModel(property));
		replace(editor);
		RBAjaxTarget.add(editor);
	}

}
