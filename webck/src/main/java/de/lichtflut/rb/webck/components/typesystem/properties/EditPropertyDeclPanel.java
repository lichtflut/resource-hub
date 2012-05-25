/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
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

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.components.typesystem.constraints.ConstraintEditorPanel;
import de.lichtflut.rb.webck.components.typesystem.constraints.ConstraintsEditorPanel;

/**
 * <p>
 *  Panel for editing {@link PropertyDeclaration}s
 * </p>
 *
 * <p>
 * 	Created Apr 3, 2012
 * </p>
 *
 * @author Ravi Knox
 */
//TODO Introduce FeedbackPanel, replace infoBox and change test accordingly
public class EditPropertyDeclPanel extends Panel {

	private IModel<PropertyRow> decl;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * @param id
	 * @param decl
	 */
	public EditPropertyDeclPanel(String id, IModel<PropertyRow> decl) {
		super(id, decl);
		this.decl = decl;
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
	 * @param form 
	 * @param target
	 */
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	}

	/**
	 * Execute on onCancel.
	 * @param form 
	 * @param target 
	 */
	protected void onCancel(AjaxRequestTarget target, Form<?> form) {
	}
	
	/**
	 * Updates all PropertyDecls if multiple PropertyDecls were changed.
	 * 
	 * @param propertyRow
	 */
	protected void updateDecls(PropertyRow propertyRow) {
//		List<Constraint> constraints = new ArrayList<Constraint>();
//		if(propertyRow.hasConstraint()){
//			if (propertyRow.isResourceReference()) {
//				constraints.add(ConstraintBuilder.buildResourceConstraint(constraintsModel.getObject()
//						.getResourceConstraint()));
//			} else {
//				constraints.add(ConstraintBuilder.buildLiteralConstraint(constraintsModel.getObject()
//						.getLiteralConstraint()));
//			}
//		}
//		propertyRow.setCardinality(cardinalityModel.getObject());
//		propertyRow.setDataType(dataTypeModel.getObject());
//		if (constraintsModel.getObject().isResourceReference()) {
//			propertyRow.setResourceConstraint(constraintsModel.getObject().getResourceConstraint());
//		} else {
//			propertyRow.setLiteralConstraint(constraintsModel.getObject().getLiteralConstraint());
//		}
	}

	// ------------------------------------------------------

	/**
	 * Adds 'save' and 'cancel' button
	 */
	private void addButtonBar(Form<?> form) {
		Button save = new RBStandardButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				updateDecls(decl.getObject());
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
	 * @param form
	 */
	private void addInfo(IModel<String> model) {
		Label label = new Label("info", model);
		this.add(label);
	}

	/**
	 * All PropertyDecls can be edited.
	 */
	private void addProperties(Form<?> form, IModel<PropertyRow> decl) {
		addInfo(Model.of(""));
		IModel<PropertyRow> constraintsModel = new PropertyModel<PropertyRow>(decl, "literalConstraint");
		if (decl.getObject().hasConstraint()) {
			if (null != decl.getObject().getResourceConstraint()) {
				constraintsModel = new PropertyModel<PropertyRow>(decl, "resourceReference");
			}
		}
		Component propertyDescriptorField = new PropertyPickerField("propertyDescriptor", new PropertyModel<ResourceID>(decl, "propertyDescriptor"));
		
		TextField<String> fieldLabelTField = new TextField<String>("fieldLabel", new PropertyModel<String>(decl, "defaultLabel"));
		
		TextField<String> cardinalityTField = new TextField<String>("cardinality", new PropertyModel<String>(decl, "cardinality"));
		
		DropDownChoice<Datatype> datatypeDDC = new DropDownChoice<Datatype>("datatype", new PropertyModel<Datatype>(decl, "dataType"),
				 Arrays.asList(Datatype.values()), new EnumChoiceRenderer<Datatype>(form));
		
		ConstraintsEditorPanel	constraintsDPicker = new ConstraintsEditorPanel("constraints", decl);
		constraintsDPicker.setOutputMarkupId(true);
		form.add(propertyDescriptorField, fieldLabelTField, cardinalityTField, datatypeDDC, constraintsDPicker);
	}

}
