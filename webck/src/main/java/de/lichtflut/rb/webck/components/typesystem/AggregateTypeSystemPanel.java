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
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.typesystem.constraints.AggregatePublicConstraintsEditorPanel;
import de.lichtflut.rb.webck.components.typesystem.properties.AggregatePropertyDeclEditorPanel;
import de.lichtflut.rb.webck.components.typesystem.schema.AggregateSchemaEditor;

/**
 * Aggregation of all components necessary to edit {@link ResourceSchema}s,
 * {@link PropertyDeclaration}s and {@link Constraint}s.
 * <br>
 * Created: Apr 23, 2012
 *
 * @author Ravi Knox
 */
public class AggregateTypeSystemPanel extends Panel{

	// ---------------- Constructor -------------------------
	
	/**
	 * Constructor.
	 * @param id - wicket:id
	 */
	public AggregateTypeSystemPanel(String id) {
		super(id);
		add(new AjaxTabbedPanel("tabs", getTabs()));
	}

	/**
	 * @return
	 */
	protected List<ITab> getTabs() {
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AbstractTab(Model.of("Resource Schema")) {
			
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new AggregateSchemaEditor(panelId);
			}
		});
		tabs.add(new AbstractTab(Model.of("Property Declaration")) {
			
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new AggregatePropertyDeclEditorPanel(panelId);
			}
		});
		tabs.add(new AbstractTab(Model.of("Public Constraints")) {
			
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new AggregatePublicConstraintsEditorPanel(panelId);
			}
		});
		return tabs;
	}

	// ------------------------------------------------------
	
	
}
