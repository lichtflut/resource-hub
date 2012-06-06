/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
import org.apache.wicket.spring.injection.annot.SpringBean;

import scala.actors.threadpool.Arrays;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.impl.ReferenceConstraint;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.form.RBButtonBar;

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
	private ServiceProvider provider;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * Constructor.
	 */
	public PublicConstraintsDetailPanel(String id, IModel<Constraint> model) {
		super(id, model);
		Form<?> form = new Form<Void>("form");
		form.add(createTitle(model));
		addFieldsToForm(form, model);
		addButtonBar(form, model);
		add(form);
		add(new FeedbackPanel("feedbackPanel"));
		setOutputMarkupPlaceholderTag(true);
	}

	// ------------------------------------------------------
	
	private Component createTitle(IModel<Constraint> model) {
		return new Label("title", new ResourceModel("constraint-title", "Edit Constraint").getObject() + model.getObject().getName());
	}
	
	protected void save(IModel<Constraint> constraint) {
		getProvider().getSchemaManager().store(constraint.getObject());
		info(getString("saved-successfully"));
	}
	
	protected void delete(IModel<Constraint> constraint) {
		getProvider().getSchemaManager().remove(constraint.getObject());
		info(getString("deleted-successfully"));
	}
	
	// ------------------------------------------------------
	
	protected void addButtonBar(Form<?> form, final IModel<Constraint> model) {
		RBButtonBar buttonBar = new RBButtonBar("buttonBar"){
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form){
				save(model);
				updatePanel();
			}
			@Override
			protected void onDelete(AjaxRequestTarget target, Form<?> form){
				delete(model);
				updatePanel();
			}
			@Override
			protected void onCancel(AjaxRequestTarget target, Form<?> form) {
				PublicConstraintsDetailPanel.this.setVisible(false);
				updatePanel();
				System.out.println("GGG");
			}
		};
		form.add(buttonBar);
	}

	private void addFieldsToForm(Form<?> form, IModel<Constraint> model) {
		final ReferenceConstraint constraint = (ReferenceConstraint) model.getObject();
		AjaxEditableLabel<String> name = new AjaxEditableLabel<String>("name", new PropertyModel<String>(constraint, "name"));
		final IModel<List<Datatype>> chosen = new PropertyModel<List<Datatype>>(constraint, "applicableDatatypes");
		@SuppressWarnings("unchecked")
		ListMultipleChoice<Datatype> datatypes = new ListMultipleChoice<Datatype>("datatypes", chosen, Model.of(Arrays.asList(Datatype.values())),  new EnumChoiceRenderer<Datatype>(this));
		AjaxEditableLabel<String> pattern = new AjaxEditableLabel<String>("pattern", new PropertyModel<String>(constraint, "literalConstraint"));
		datatypes.setMaxRows(10);
		form.add(name, datatypes, pattern);
	}
	
	private ServiceProvider getProvider(){
		return provider;
	}

	private void updatePanel() {
		RBAjaxTarget.add(PublicConstraintsDetailPanel.this);
	}
}
