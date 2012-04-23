/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.schema;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.typesystem.TypeBrowserPanel;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemHelpPanel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;
import de.lichtflut.rb.webck.models.types.SNClassListModel;

/**
 * This Panel aggregates all components necessary for editing
 * {@link ResourceSchema}s.
 * <br>
 * Created: Apr 23, 2012
 * 
 * @author Ravi Knox
 */
public class AggregateSchemaEditor extends Panel {

	@SpringBean
	ServiceProvider provider;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * @param id
	 */
	public AggregateSchemaEditor(String id) {
		super(id);
		createTypesPanel();
		add(new TypeSystemHelpPanel("schemaDetails").setOutputMarkupId(true));

	}

	// ------------------------------------------------------

	/**
	 * display all RDF:TYPES that exist.
	 */
	private void createTypesPanel() {
		// IModel<ResourceID> type = new Model<ResourceID>();
		// this.add(new ResourcePickerField("typePicker", type, RBSystem.TYPE));
		this.add(new Label("typePicker", Model.of("Search...")));
		this.add(new TypeBrowserPanel("typeBrowser", new SNClassListModel()) {
			@Override
			public void onTypeSelected(SNClass type, AjaxRequestTarget target) {
				displaySchema(type);
			}
		});
	}

	/**
	 * Displays a detailed view for a type.
	 * 
	 * @param type
	 *            to display
	 */
	private void displaySchema(final SNClass type) {
		final IModel<ResourceSchema> model = new AbstractLoadableModel<ResourceSchema>() {
			@Override
			public ResourceSchema load() {
				final ResourceSchema existing = provider.getSchemaManager()
						.findSchemaForType(type);
				if (existing != null) {
					return existing;
				} else {
					return new ResourceSchemaImpl(type);
				}
			}
		};
		SchemaDetailPanel schemaDetails = new SchemaDetailPanel(
				"schemaDetails", model);
		replace(schemaDetails);
		RBAjaxTarget.add(schemaDetails);
	}

}
