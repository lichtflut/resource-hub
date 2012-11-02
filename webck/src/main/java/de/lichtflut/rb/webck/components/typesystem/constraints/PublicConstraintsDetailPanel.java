/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.ConstraintImpl;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.components.form.RBButtonBar;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 * This {@link Panel} shows detailed information of a public constraint.
 * </p>
 * Created: Jun 5, 2012
 *
 * @author Ravi Knox
 */
public class PublicConstraintsDetailPanel extends Panel{

	@SpringBean
	private SchemaManager schemaManager;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 */
	public PublicConstraintsDetailPanel(final String id, final IModel<Constraint> model) {
		super(id, model);

		Form<?> form = new Form<Void>("form");
		addTitle(form, model);
		addFields(form, model);
		addButtonBar(form, model);

		add(form);
		add(new FeedbackPanel("feedbackPanel"));

		setOutputMarkupPlaceholderTag(true);
	}

	// ------------------------------------------------------

	/**
	 * Execute when 'save'-Button is clicked
	 * @param constraint
	 */
	protected void save(final IModel<Constraint> constraint) {
		schemaManager.store(constraint.getObject());
		info(getString("saved-successfully"));
		send(getPage(), Broadcast.BUBBLE, new ModelChangeEvent<Constraint>(constraint.getObject(), ModelChangeEvent.PUBLIC_CONSTRAINT));
	}

	/**
	 * Execute when 'delete'-Button is clicked
	 * @param constraint
	 */
	protected void delete(final IModel<Constraint> constraint) {
		schemaManager.remove(constraint.getObject());
	}

	/**
	 * @return the {@link DialogHoster}
	 */
	protected DialogHoster getDialogHoster() {
		DialogHoster hoster = findParent(DialogHoster.class);
		return hoster;
	}

	// ------------------------------------------------------

	private void addTitle(final Form<?> form, final IModel<Constraint> model) {
		String title = new ResourceModel("constraint-title", "Edit Constraint").getObject();
		form.add(new Label("title", title + ": " + model.getObject().getName()));
	}

	protected void addButtonBar(final Form<?> form, final IModel<Constraint> model) {
		RBButtonBar buttonBar = new RBButtonBar("buttonBar"){
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form){
				save(model);
				updatePanel();
			}
			@Override
			protected void onDelete(final AjaxRequestTarget target, final Form<?> form){
				DialogHoster hoster = getDialogHoster();
				ConfirmationDialog dialog = new ConfirmationDialog(hoster.getDialogID(), Model.of(getString("delete-confirmation"))){
					@Override
					public void onConfirm(){
						delete(model);
						send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.PUBLIC_CONSTRAINT));
						PublicConstraintsDetailPanel.this.setVisible(false);
						updatePanel();
					}
				};
				hoster.openDialog(dialog);
			}

			@Override
			protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
				PublicConstraintsDetailPanel.this.setVisible(false);
				updatePanel();
			}
		};
		form.add(buttonBar);
	}

	private void addFields(final Form<?> form, final IModel<Constraint> model) {
		final ConstraintImpl constraint = (ConstraintImpl) model.getObject();
		AjaxEditableLabel<String> name = new AjaxEditableLabel<String>("name", new PropertyModel<String>(constraint, "name"));
		final IModel<List<Datatype>> chosen = new PropertyModel<List<Datatype>>(constraint, "applicableDatatypes");
		IModel<List<Datatype>> allDatatypes = new ListModel<Datatype>(Arrays.<Datatype>asList(Datatype.values()));

		ListMultipleChoice<Datatype> datatypes =
				new ListMultipleChoice<Datatype>("datatypes", chosen, allDatatypes, new EnumChoiceRenderer<Datatype>(this));

		AjaxEditableLabel<String> pattern = new AjaxEditableLabel<String>("pattern", new PropertyModel<String>(constraint, "literalConstraint"));
		datatypes.setMaxRows(10);
		form.add(name, datatypes, pattern);
	}

	private void updatePanel() {
		RBAjaxTarget.add(PublicConstraintsDetailPanel.this);
	}
}
