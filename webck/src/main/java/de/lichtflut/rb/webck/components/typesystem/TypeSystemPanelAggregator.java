/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
