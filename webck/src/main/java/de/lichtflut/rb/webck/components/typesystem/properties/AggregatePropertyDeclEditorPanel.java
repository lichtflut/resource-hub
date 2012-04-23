/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.properties;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.arastreju.sge.model.nodes.views.SNProperty;

import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemHelpPanel;
import de.lichtflut.rb.webck.models.types.SNPropertyListModel;
import de.lichtflut.rb.webck.models.types.SNPropertyModel;

/**
 * [TODO Insert description here.]
 * <br>
 * Created: Apr 23, 2012
 *
 * @author Ravi Knox
 */
public class AggregatePropertyDeclEditorPanel extends Panel {

	// ---------------- Constructor -------------------------
	
	/**
	 * @param id - wicket:id
	 */
	public AggregatePropertyDeclEditorPanel(String id) {
		super(id);
		add(new TypeSystemHelpPanel("editor").setOutputMarkupId(true));
		add(new SNPropertyBrowserPanel("propertyBrowser", new SNPropertyListModel()) {
			@Override
			public void onPropertySelected(final SNProperty property, final AjaxRequestTarget target) {
				displayPropertyEditor(property);
			}

			@Override
			public void onCreateProperty(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	protected void displayPropertyEditor(final SNProperty property) {
		final SNPropertyEditorPanel editor = new SNPropertyEditorPanel("editor", new SNPropertyModel(property));
		replace(editor);
		RBAjaxTarget.add(editor);
	}

}
