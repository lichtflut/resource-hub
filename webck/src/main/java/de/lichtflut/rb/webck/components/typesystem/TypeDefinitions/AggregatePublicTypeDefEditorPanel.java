/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.TypeDefinitions;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemHelpPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.types.PublicTypeDefListModel;

/**
 * [TODO Insert description here.]
 * <br>
 * Created: Apr 23, 2012
 *
 * @author Ravi Knox
 */
public class AggregatePublicTypeDefEditorPanel extends Panel {

	@SpringBean
	private ServiceProvider provider;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * @param id
	 */
	public AggregatePublicTypeDefEditorPanel(String id) {
		super(id);
		add(new TypeSystemHelpPanel("editor").setOutputMarkupId(true));
		add(new Label("searchbox", Model.of("Search...")));
		add(new TypeDefBrowserPanel("publicTypeDefBrowser", new PublicTypeDefListModel()) {
			@Override
			public void onCreateTypeDef(AjaxRequestTarget target) {
			}
			@Override
			public void onTypeDefSelected(final TypeDefinition def, final AjaxRequestTarget target) {
				displayTypeDefEditor(def);
			}
		});
	}

	// ------------------------------------------------------
	
	protected void displayTypeDefEditor(final TypeDefinition def) {
		final TypeDefinition reloaded = schemaManager().findTypeDefinition(def.getID());
		final IModel<PropertyRow> model = Model.of(new PropertyRow(reloaded));
		final TypeDefEditorPanel editor = new TypeDefEditorPanel("editor", model) {
			@Override
			public void onSave(final AjaxRequestTarget target) {
				final TypeDefinition definition = PropertyRow.toTypeDefinition(model.getObject());
				schemaManager().store(definition);
				send(getPage(), Broadcast.BUBBLE, new ModelChangeEvent<TypeDefinition>(def, ModelChangeEvent.PUBLIC_TYPE_DEFINITION));
			}
		};
		replace(editor);
		RBAjaxTarget.add(editor);
	}
	
	private SchemaManager schemaManager(){
		return provider.getSchemaManager();
	}
}
