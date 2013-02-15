/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.fields.PropertyPickerField;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.EditVisualizationInfoPanel;
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
public class EditPropertyDeclPanel extends Panel {

	@SpringBean
	private SchemaManager schemaManager;

	private final IModel<ResourceSchema> schema;
	private final IModel<PropertyRow> declaration;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor
	 * @param id - wicket:id
	 * @param decl
	 */
	public EditPropertyDeclPanel(final String id, final IModel<PropertyRow> declaration, final IModel<ResourceSchema> schema) {
		super(id, declaration);
		this.schema = schema;
		this.declaration = declaration;

		Form<?> form = new Form<Void>("form");

		add(new FeedbackPanel("feedback").setEscapeModelStrings(false));

		addPropertiyEditor(form);
		form.add(new EditVisualizationInfoPanel("visualizationInfoPanel", declaration));

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
	protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
	}

	/**
	 * Execute on onCancel.
	 * 
	 * @param form
	 * @param target
	 */
	protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
	}

	// ------------------------------------------------------

	/**
	 * Adds 'save' and 'cancel' button
	 */
	private void addButtonBar(final Form<?> form) {
		Button save = new RBStandardButton("save") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				try{
					schema.getObject().removePropertyDeclaration(declaration.getObject().asPropertyDeclaration());
					schema.getObject().addPropertyDeclaration(declaration.getObject().asPropertyDeclaration());
					List<Integer> errors = filterErrors(schemaManager.validate(schema.getObject()));
					if(!errors.isEmpty()){
						error(buildValidationErrorMessage(errors));
					}else{
						EditPropertyDeclPanel.this.onSubmit(target, form);
					}
					updatePanel();
				}catch (IllegalArgumentException e){
					error(getString("error.add-property"));
					updatePanel();
				}
			}

			private void removeExisting(final IModel<ResourceSchema> schema, final IModel<PropertyRow> declaration) {
				PropertyDeclaration updated = declaration.getObject().asPropertyDeclaration();
				for (PropertyDeclaration decl : schema.getObject().getPropertyDeclarations()) {
					if(decl.getPropertyDescriptor().equals(updated.getPropertyDescriptor())){
						schema.getObject().getPropertyDeclarations().remove(decl);
						break;
					}
				}
			}
		};
		Button cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				onCancel(target, form);
			}
		};
		save.add(new Label("saveLabel", new ResourceModel("button.ok", "Okay!")));
		cancel.add(new Label("cancelLabel", new ResourceModel("button.cancel", "Cancel")));
		form.add(save, cancel);
	}

	/**
	 * Add all attributes directly relating to a {@link PropertyDeclaration}.
	 */
	private void addPropertiyEditor(final Form<?> form) {
		if(declaration.getObject().getPropertyDescriptor() == null){
			declaration.getObject().setPropertyDescriptor(new SimpleResourceID(""));
		}
		PropertyPickerField propertyDescriptorField = new PropertyPickerField("propertyDescriptor", new PropertyModel<ResourceID>(declaration,
				"propertyDescriptor"));
		propertyDescriptorField.getDisplayComponent().setRequired(true);

		TextField<String> fieldLabelTField = new TextField<String>("fieldLabel", new PropertyModel<String>(declaration, "defaultLabel"));

		TextField<String> cardinalityTField = new TextField<String>("cardinality", new PropertyModel<String>(declaration, "cardinality"));

		final ConstraintsEditorPanel constraintsPicker = new ConstraintsEditorPanel("constraints", declaration);
		constraintsPicker.setOutputMarkupId(true);

		final PropertyModel<Datatype> datatypeModel = new PropertyModel<Datatype>(declaration, "dataType");
		final DropDownChoice<Datatype> datatypeDDC = new DropDownChoice<Datatype>("datatype", datatypeModel, Arrays.asList(Datatype.values()),
				new EnumChoiceRenderer<Datatype>(this));
		datatypeDDC.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				declaration.getObject().setDataType(datatypeModel.getObject());
			}
		});

		form.add(propertyDescriptorField, fieldLabelTField, cardinalityTField, datatypeDDC, constraintsPicker);
	}

	private void updatePanel() {
		RBAjaxTarget.add(EditPropertyDeclPanel.this);
	}

	private String buildValidationErrorMessage(final List<Integer> errors) {
		StringBuilder sb = new StringBuilder("Error occured:<ul>");
		if(errors.contains(ErrorCodes.SCHEMA_CONSTRAINT_EXCEPTION)){
			sb.append("<li>Constraint is not allowed for " +  declaration.getObject().getDataType() + "</li>");
		}
		sb.append("</ul>");
		return sb.toString();
	}

	private List<Integer> filterErrors(final Map<Integer, List<PropertyDeclaration>> errors) {
		List<Integer> filtered = new ArrayList<Integer>();
		String currentDeclURI = declaration.getObject().asPropertyDeclaration().getPropertyDescriptor().getQualifiedName().toURI();
		for (Integer errorCode : errors.keySet()) {
			// Get errors.get(errorCode).contains(declaration) working instead of manually checking..
			for (PropertyDeclaration decl : errors.get(errorCode)) {
				if(currentDeclURI.equals(decl.getPropertyDescriptor().getQualifiedName().toURI())) {
					filtered.add(errorCode);
				}
			}
		}
		return filtered;
	}

}
