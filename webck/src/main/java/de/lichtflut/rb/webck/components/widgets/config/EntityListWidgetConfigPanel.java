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
package de.lichtflut.rb.webck.components.widgets.config;

import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.widgets.EntityListWidget;
import de.lichtflut.rb.webck.components.widgets.config.actions.ActionsConfigPanel;
import de.lichtflut.rb.webck.components.widgets.config.columns.ColumnsConfigPanel;
import de.lichtflut.rb.webck.components.widgets.config.selection.SelectionConfigPanel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.model.IModel;

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
	 * Constructor.
	 * @param id
	 * @param model
	 */
	public EntityListWidgetConfigPanel(String id, IModel<WidgetSpec> model) {
		super(id, model);
		
		getForm().add(new SelectionConfigPanel("selection", new DerivedModel<Selection, WidgetSpec>(model) {
			@Override
			protected Selection derive(WidgetSpec widgetSpec) {
				return widgetSpec.getSelection();
			}	
		}));
		
		getForm().add(new ColumnsConfigPanel("columns", model));
		
		getForm().add(new ActionsConfigPanel("actions", model));
	}
	
}
