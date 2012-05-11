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

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemHelpPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.types.PublicConstraintsListModel;

/**
 * [TODO Insert description here.]
 * <br>
 * Created: Apr 23, 2012
 *
 * @author Ravi Knox
 */
public class AggregatePublicConstraintsEditorPanel extends Panel {

	@SpringBean
	private ServiceProvider provider;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * @param id
	 */
	public AggregatePublicConstraintsEditorPanel(String id) {
		super(id);
		add(new TypeSystemHelpPanel("editor").setOutputMarkupId(true));
		add(new Label("searchbox", Model.of("Search...")));
		add(new ConstraintsBrowserPanel("publicConstraintsBrowser", new PublicConstraintsListModel()) {
			@Override
			public void onCreateConstraint(AjaxRequestTarget target) {
			}
			@Override
			public void onConstraintSelected(final Constraint constraint, final AjaxRequestTarget target) {
				displayConstraintEditor(constraint);
			}
		});
	}

	// ------------------------------------------------------
	
	protected void displayConstraintEditor(final Constraint constraint) {
		final Constraint reloaded = schemaManager().findConstraint(constraint.getID());
		final IModel<PropertyRow> model = Model.of(new PropertyRow(reloaded));
		final ConstraintEditorPanel editor = new ConstraintEditorPanel("editor", model) {
			@Override
			public void onSave(final AjaxRequestTarget target) {
				final Constraint constraint = PropertyRow.toPublicConstraint(model.getObject());
				schemaManager().store(constraint);
				send(getPage(), Broadcast.BUBBLE, new ModelChangeEvent<Constraint>(constraint, ModelChangeEvent.PUBLIC_TYPE_DEFINITION));
			}
		};
		replace(editor);
		RBAjaxTarget.add(editor);
	}
	
	private SchemaManager schemaManager(){
		return provider.getSchemaManager();
	}
}
