/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.schema;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.CreateTypeDialog;
import de.lichtflut.rb.webck.components.typesystem.TypeBrowserPanel;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemHelpPanel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;
import de.lichtflut.rb.webck.models.types.SNClassListModel;

/**
 * This Panel aggregates all components necessary for editing
 * {@link ResourceSchema}s. <br>
 * Created: Apr 23, 2012
 * 
 * @author Ravi Knox
 */
public class SchemaEditorAggregator extends Panel {

	@SpringBean
	private SchemaManager schemaManager;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 */
	public SchemaEditorAggregator(String id) {
		super(id);
		createTypesPanel();
		add(new TypeSystemHelpPanel("schemaDetails").setOutputMarkupId(true));
	}

	// ------------------------------------------------------

	/**
	 * display all RDF:TYPES that exist.
	 */
	private void createTypesPanel() {
		// TODO: Add search Panel
		// IModel<ResourceID> type = new Model<ResourceID>();
		// this.add(new ResourcePickerField("typePicker", type, RBSystem.TYPE));
		this.add(new TypeBrowserPanel("typeBrowser", new SNClassListModel()) {
			@Override
			protected void onCreate(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreateTypeDialog(hoster.getDialogID()));
			}

			@Override
			public void onTypeSelected(SNClass type, AjaxRequestTarget target) {
				displaySchema(type);
			}
		});
	}

	/**
	 * Displays a detailed view for a type.
	 */
	private void displaySchema(final SNClass type) {
		final IModel<ResourceSchema> model = new AbstractLoadableModel<ResourceSchema>() {
			@Override
			public ResourceSchema load() {
				final ResourceSchema existing = schemaManager.findSchemaForType(type);
				if (existing != null) {
					return existing;
				} else {
					return new ResourceSchemaImpl(type);
				}
			}
		};
		SchemaDetailPanel schemaDetails = new SchemaDetailPanel("schemaDetails", model);
		replace(schemaDetails);
		RBAjaxTarget.add(schemaDetails);
	}

}
