/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.properties;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.constraints.ConstraintsEditorPanel;

/**
 * <p>
 * Panel for editing {@link PropertyDeclaration}s
 * </p>
 * 
 * <p>
 * Created Apr 3, 2012
 * </p>
 * 
 * @author Ravi Knox
 */
// TODO Introduce FeedbackPanel, replace infoBox and change test accordingly
public class EditPropertyDeclPanel extends Panel {

	// ---------------- Constructor -------------------------

	/**
	 * @param id
	 * @param decl
	 */
	public EditPropertyDeclPanel(String id, IModel<PropertyRow> decl) {
		super(id, decl);
		@SuppressWarnings("rawtypes")
		Form<?> form = new Form("form");
		addProperties(form, decl);
		add(form);
		addButtonBar(form);
		setOutputMarkupId(true);
	}

	// ------------------------------------------------------

	/**
	 * Execute on onSubmit.
	 * 
	 * @param form
	 * @param target
	 */
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	}

	/**
	 * Execute on onCancel.
	 * 
	 * @param form
	 * @param target
	 */
	protected void onCancel(AjaxRequestTarget target, Form<?> form) {
	}

	// ------------------------------------------------------

	/**
	 * Adds 'save' and 'cancel' button
	 */
	private void addButtonBar(Form<?> form) {
		Button save = new RBStandardButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				EditPropertyDeclPanel.this.onSubmit(target, form);
			}
		};
		Button cancel = new RBStandardButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onCancel(target, form);
			}
		};
		save.add(new Label("saveLabel", new ResourceModel("button.save", "Save")));
		cancel.add(new Label("cancelLabel", new ResourceModel("button.cancel", "Cancel")));
		form.add(save, cancel);
	}

	/**
	 * Add Infopanel
	 */
	private void addInfo(IModel<String> model) {
		Label label = new Label("info", model);
		this.add(label);
	}

	/**
	 * All PropertyDecls can be edited.
	 */
	private void addProperties(Form<?> form, final IModel<PropertyRow> decl) {
		addInfo(Model.of(""));

		Component propertyDescriptorField = new PropertyPickerField("propertyDescriptor", new PropertyModel<ResourceID>(decl,
				"propertyDescriptor"));

		TextField<String> fieldLabelTField = new TextField<String>("fieldLabel", new PropertyModel<String>(decl, "defaultLabel"));

		TextField<String> cardinalityTField = new TextField<String>("cardinality", new PropertyModel<String>(decl, "cardinality"));

		final PropertyModel<Datatype> datatypeModel = new PropertyModel<Datatype>(decl, "dataType");
		DropDownChoice<Datatype> datatypeDDC = new DropDownChoice<Datatype>("datatype", datatypeModel, Arrays.asList(Datatype.values()),
				new EnumChoiceRenderer<Datatype>(form));
		datatypeDDC.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				decl.getObject().setDataType(datatypeModel.getObject());
				get("form:constraints").replaceWith(new ConstraintsEditorPanel("constraints", decl));
				RBAjaxTarget.add(EditPropertyDeclPanel.this);
			}
		});
		ConstraintsEditorPanel constraintsDPicker = new ConstraintsEditorPanel("constraints", decl);
		String s = constraintsDPicker.getPageRelativePath();
		System.out.println(s);
		constraintsDPicker.setOutputMarkupId(true);
		form.add(propertyDescriptorField, fieldLabelTField, cardinalityTField, datatypeDDC, constraintsDPicker);
	}
}
