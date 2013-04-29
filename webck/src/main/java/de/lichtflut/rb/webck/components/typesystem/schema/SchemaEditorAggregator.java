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
package de.lichtflut.rb.webck.components.typesystem.schema;

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
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.views.SNClass;

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
