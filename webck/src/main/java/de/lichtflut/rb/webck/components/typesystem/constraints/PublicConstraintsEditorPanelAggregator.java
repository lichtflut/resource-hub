/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.CreatePublicConstraintDialog;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.TypeSystemHelpPanel;
import de.lichtflut.rb.webck.models.types.PublicConstraintsListModel;

/**
 * <p>
 * This is an aggregator {@link Panel} that contains everything necessary to manage public
 * constraints.
 * </p>
 * Created: Apr 23, 2012
 * 
 * @author Ravi Knox
 */
public class PublicConstraintsEditorPanelAggregator extends Panel {

	@SpringBean
	private ServiceProvider provider;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id - wicket:id
	 */
	public PublicConstraintsEditorPanelAggregator(String id) {
		super(id);
		add(new TypeSystemHelpPanel("editor").setOutputMarkupId(true));
		add(new Label("searchbox", Model.of("Search...")));
		add(new ConstraintsBrowserPanel("publicConstraintsBrowser", new PublicConstraintsListModel()) {
			@Override
			public void onCreateConstraint(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new CreatePublicConstraintDialog(hoster.getDialogID()));
			}

			@Override
			public void onConstraintSelected(final Constraint constraint, final AjaxRequestTarget target) {
				displayConstraintEditor(constraint);
			}
		});
	}

	// ------------------------------------------------------

	protected void displayConstraintEditor(final Constraint constraint) {
		final Constraint reloaded = schemaManager().findConstraint(constraint.asResourceNode());
		final IModel<PropertyRow> model = Model.of(new PropertyRow(reloaded));
		final Panel editor = new PublicConstraintsDetailPanel("editor", Model.of(model.getObject().asPropertyDeclaration().getConstraint()));
		replace(editor);
		RBAjaxTarget.add(editor);
	}

	// ------------------------------------------------------

	private SchemaManager schemaManager() {
		return provider.getSchemaManager();
	}

}
