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
package de.lichtflut.rb.webck.components.typesystem;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.typesystem.constraints.PublicConstraintsEditorPanelAggregator;
import de.lichtflut.rb.webck.components.typesystem.properties.PropertyDeclEditorPanelAggregator;
import de.lichtflut.rb.webck.components.typesystem.schema.SchemaEditorAggregator;

/**
 * Aggregation of all components necessary to edit {@link ResourceSchema}s,
 * {@link PropertyDeclaration}s and {@link Constraint}s. <br>
 * Created: Apr 23, 2012
 * 
 * @author Ravi Knox
 */
public class TypeSystemPanelAggregator extends Panel {

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id - wicket:id
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TypeSystemPanelAggregator(final String id) {
		super(id);
		add(new AjaxTabbedPanel("tabs", getTabs()));
	}

	// ------------------------------------------------------

	/**
	 * Create and initialize {@link ITab}s.
	 * 
	 * @return a {@link List} of {@link ITab}s
	 */
	protected List<ITab> getTabs() {
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AbstractTab(new ResourceModel("tab.title.type")) {

			@Override
			public WebMarkupContainer getPanel(final String panelId) {
				return new SchemaEditorAggregator(panelId);
			}
		});
		tabs.add(new AbstractTab(new ResourceModel("tab.title.property")) {

			@Override
			public WebMarkupContainer getPanel(final String panelId) {
				return new PropertyDeclEditorPanelAggregator(panelId);
			}
		});
		tabs.add(new AbstractTab(new ResourceModel("tab.title.constraint")) {

			@Override
			public WebMarkupContainer getPanel(final String panelId) {
				return new PublicConstraintsEditorPanelAggregator(panelId);
			}
		});
		return tabs;
	}

	// ------------------------------------------------------

}
