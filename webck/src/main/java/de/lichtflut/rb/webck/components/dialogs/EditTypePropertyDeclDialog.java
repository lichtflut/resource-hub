/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.ConstraintsEditorPanel;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import de.lichtflut.rb.webck.models.basic.DerivedModel;


/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Mar 1, 2012
 * </p>
 *
 * @author Ravi Knox
 */
// TODO THIS IS NOT YET IMPLEMENTED PROPERLY!
@SuppressWarnings("rawtypes")
public class EditTypePropertyDeclDialog extends AbstractRBDialog {

	
	// TODO Introduce FeedbackPanel, replace infoBox
	
	IModel<Boolean> multiple;
	
	/**
	 * @param id - wicket:id
	 * @param decls
	 */
	public EditTypePropertyDeclDialog(String id, IModel<List<PropertyDeclaration>> decls) {
		super(id);
		Form form = new Form("form");
		if(decls.getObject().size() == 1){
			multiple = Model.of(false);
			addFullTypeDecl(form, decls);
		}else if(decls.getObject().size() > 1){
			multiple = Model.of(true);
			addReducedTypeDecl(form, decls);
		}else{
			addEmptyListWarning(form);
		}
		add(form);
	}

	// ------------------------------------------------------
	
	/**
	 * @param form
	 */
	private void addEmptyListWarning(Form form) {
		form.setVisible(false);
		addInfo(new ResourceModel("error.empty-list"));
		addButtonBar(form);
	}

	/**
	 * 
	 */
	private void addButtonBar(Form form) {
		Button save = new RBStandardButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				EditTypePropertyDeclDialog.this.applyActions();
			}
		};
		Button cancel = new RBStandardButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				setDefaultFormProcessing(false);
			}
		};
		save.add(new Label("saveLabel", new ResourceModel("button.save", "Save")));
		cancel.add(new Label("cancelLabel", new ResourceModel("button.cancel", "Cancel")));
		form.add(save, cancel);
	}

	/**
	 * 
	 */
	protected void applyActions() {
		// TODO: applyActions
	}

	/**
	 * @param form
	 */
	private void addInfo(IModel<String> model) {
		Label label = new Label("info", model);
		this.add(label);
	}

	/**
	 * 
	 */
	private void addReducedTypeDecl(Form form, IModel<List<PropertyDeclaration>> decls) {
		addInfo(Model.of(""));
		PropertyRow row = new PropertyRow(decls.getObject().get(0));
		IModel<String> labelModel = Model.of(concatProperties(decls));
		Label f1 = new Label("propertyDescriptor",labelModel);
		TextField<String> f2 = new TextField<String>("fieldLabel", Model.of(""));
		f2.setVisible(false);
		TextField<Integer> f3 = new TextField<Integer>("min", new PropertyModel<Integer>(row, "min"));
		TextField<Integer> f4 = new TextField<Integer>("max", new PropertyModel<Integer>(row, "max"));
		DropDownChoice<Datatype> f5 = new DropDownChoice<Datatype>("datatype", new PropertyModel<Datatype>(row, "dataType"), 
				 Arrays.asList(Datatype.values()), new EnumChoiceRenderer<Datatype>(form));
		TextField<ResourceID> f6;
		if (row.isResourceReference()) {
			f6 = new TextField<ResourceID>("constraints", new PropertyModel<ResourceID>(row, "resourceConstraint"));
		} else {
			f6 = new TextField<ResourceID>("constraints", new PropertyModel<ResourceID>(row, "literalConstraints"));
		}
		form.add(f1, f2, f3, f4, f5, f6);
	}

	/**
	 * @param decls
	 * @return
	 */
	private String concatProperties(IModel<List<PropertyDeclaration>> decls) {
		StringBuilder sb = new StringBuilder();
		for (PropertyDeclaration decl : decls.getObject()) {
			sb.append(ResourceLabelBuilder.getInstance().getFieldLabel(decl.getPropertyDescriptor(), getLocale()));
		}
		return sb.toString();
	}

	/**
	 * 
	 */
	private void addFullTypeDecl(Form form, IModel<List<PropertyDeclaration>> decls) {
		addInfo(Model.of(""));
		PropertyRow row = new PropertyRow(decls.getObject().get(0));
		IModel<String> propertyDesc = new DerivedModel<String, ResourceID>(row.getPropertyDescriptor()) {
			@Override
			protected String derive(ResourceID original) {
				return ResourceLabelBuilder.getInstance().getFieldLabel(original, getLocale());
			}
		};
		Label fieldLabel = new Label("propertyDescriptor", propertyDesc);
		TextField<String> f2 = new TextField<String>("fieldLabel", new PropertyModel<String>(row, "defaultLabel"));
		TextField<Integer> f3 = new TextField<Integer>("min", new PropertyModel<Integer>(row, "min"));
		TextField<Integer> f4 = new TextField<Integer>("max", new PropertyModel<Integer>(row, "max"));
		DropDownChoice<Datatype> f5 = new DropDownChoice<Datatype>("datatype", new PropertyModel<Datatype>(row, "dataType"), 
				 Arrays.asList(Datatype.values()), new EnumChoiceRenderer<Datatype>(form));
		ConstraintsEditorPanel	f6 = new ConstraintsEditorPanel("constraints", new Model<PropertyRow>(row));
		addButtonBar(form);
		form.add(fieldLabel, f2, f3, f4, f5, f6);
	}

}
