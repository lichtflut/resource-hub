/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.widgets.EntityListWidget;
import de.lichtflut.rb.webck.components.widgets.config.actions.ActionsConfigPanel;
import de.lichtflut.rb.webck.components.widgets.config.columns.ColumnsConfigPanel;
import de.lichtflut.rb.webck.components.widgets.config.selection.SelectionConfigPanel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

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
