/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.properties;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.arastreju.sge.model.nodes.views.SNProperty;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.CreatePropertyDialog;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemHelpPanel;
import de.lichtflut.rb.webck.models.types.SNPropertyListModel;
import de.lichtflut.rb.webck.models.types.SNPropertyModel;

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
