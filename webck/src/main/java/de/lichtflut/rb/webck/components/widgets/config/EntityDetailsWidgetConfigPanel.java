/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.config;

import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.widgets.EntityListWidget;
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
public class EntityDetailsWidgetConfigPanel extends AbstractWidgetConfigPanel {
	
	/**
	 * @param id
	 * @param model
	 */
	public EntityDetailsWidgetConfigPanel(String id, IModel<WidgetSpec> model) {
		super(id, model);
		
		getForm().add(new SelectionConfigPanel("selection", new DerivedModel<Selection, WidgetSpec>(model) {
			@Override
			protected Selection derive(WidgetSpec original) {
				return original.getSelection();
			}
		}));
	}
	
}
