/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.properties;

import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
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
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.ConstraintsEditorPanel;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;

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

	private IModel<List<PropertyRow>> decls;
	private IModel<Number> number = Model.of(Number.Singular);
	private IModel<ResourceID> propertyDescModel;
	private IModel<String> fieldLabelModel, cardinalityModel;
	private IModel<Datatype> dataTypeModel;
	private IModel<PropertyRow> constraintsModel;
	private IModel<Boolean> datatypeBol, constraintsBol, cardinalityBol;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * @param id
	 * @param decls
	 */
	public EditPropertyDeclPanel(String id, IModel<List<PropertyRow>> decls) {
		super(id, decls);
		this.decls = decls;
		this.datatypeBol = Model.of(false);
		this.constraintsBol = Model.of(false);
		this.cardinalityBol = Model.of(false);
		@SuppressWarnings("rawtypes")
		Form<?> form = new Form("form");
		if(decls.getObject().size() == 1){
			addTypeDecls(form, decls);
		}else if(decls.getObject().size() > 1){
			number.setObject(Number.Plural);
			addTypeDecls(form, decls);
		}else{
			addEmptyListWarning(form);
		}
		add(form);
		addButtonBar(form);
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
	 */
	protected void updateDecls() {
		List<Constraint> constraints = new ArrayList<Constraint>();
		if(constraintsModel.getObject().isResourceReference()){
			constraints.add(ConstraintBuilder.buildResourceConstraint(constraintsModel.getObject().getResourceConstraint()));
		}else{
			for (String s : constraintsModel.getObject().getLiteralConstraints()) {
				constraints.add(ConstraintBuilder.buildLiteralConstraint(s));
			}
		}
		for (PropertyRow decl : decls.getObject()) {
			decl.setCardinality(cardinalityModel.getObject());
			decl.setDataType(dataTypeModel.getObject());
			if(constraintsModel.getObject().isResourceReference()){
				decl.setResourceConstraint(constraintsModel.getObject().getResourceConstraint());
			}else{
				decl.setLiteralConstraints(constraintsModel.getObject().getLiteralConstraints());
			}
		}
	}

	// ------------------------------------------------------
	
	/**
	 * @param form
	 */
	private void addEmptyListWarning(Form<?> form) {
		form.setVisible(false);
		addInfo(new ResourceModel("error.empty-list"));
	}

	/**
	 * Adds 'save' and 'cancel' button
	 */
	private void addButtonBar(Form<?> form) {
		Button save = new RBStandardButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				updateDecls();
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
	private void addTypeDecls(Form<?> form, IModel<List<PropertyRow>> decls) {
		addInfo(Model.of(""));
		// Initialize model values with the first entry of the list for initial values
		propertyDescModel = new PropertyModel<ResourceID>(decls.getObject().get(0), "propertyDescriptor");
		fieldLabelModel = new PropertyModel<String>(decls.getObject().get(0), "defaultLabel");
		cardinalityModel = new PropertyModel<String>(decls.getObject().get(0), "cardinality");
		dataTypeModel = new PropertyModel<Datatype>(decls.getObject().get(0), "dataType");
		constraintsModel = new Model<PropertyRow>(decls.getObject().get(0));
		Component propertyDescriptorField = null;
		if(Number.Singular.name().equals(number.getObject().name())){
			propertyDescriptorField = new PropertyPickerField("propertyDescriptor", propertyDescModel);
			propertyDescriptorField.add(ConditionalBehavior.enableIf(areEqual(number, Number.Singular)));
		}else {
			propertyDescriptorField = new Label("propertyDescriptor", concatDescriptorLabels(decls));
		}
		TextField<String> fieldLabelTField = new TextField<String>("fieldLabel", fieldLabelModel);
		fieldLabelTField.add(ConditionalBehavior.visibleIf(areEqual(number, Number.Singular)));
		TextField<String> cardinalityTField = new TextField<String>("cardinality", cardinalityModel);
		DropDownChoice<Datatype> datatypeDDC = new DropDownChoice<Datatype>("datatype", dataTypeModel,
				 Arrays.asList(Datatype.values()), new EnumChoiceRenderer<Datatype>(form));
		ConstraintsEditorPanel	constraintsDPicker = new ConstraintsEditorPanel("constraints", constraintsModel);
		form.add(new CheckBox("cardinalityCheckBox", cardinalityBol));
		form.add(new CheckBox("constraintsCheckBox", constraintsBol));
		form.add(new CheckBox("datatypeCheckBox", datatypeBol));
		form.add(propertyDescriptorField, fieldLabelTField, cardinalityTField, datatypeDDC, constraintsDPicker);
	}

	private String concatDescriptorLabels(IModel<List<PropertyRow>> decls) {
		StringBuilder sb = new StringBuilder();
		for (PropertyRow pdec : decls.getObject()) {
			sb.append(pdec.getDefaultLabel() + ", ");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}

	// ------------------------------------------------------
	
	private enum Number{
		Singular,
		Plural
	}
}
