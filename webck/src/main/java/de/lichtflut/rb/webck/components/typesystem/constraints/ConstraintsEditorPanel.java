/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;

/**
 * 
 * <p>
 * Panel for editing of Constraints.
 * </p>
 * 
 * <p>
 * Created May, 2012
 * </p>
 * 
 * @author Ravi Knox
 */
public class ConstraintsEditorPanel extends Panel {

	private IModel<ConstraintType> constraintType = Model.of(ConstraintType.LITERAL);

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 */
	public ConstraintsEditorPanel(final String id, final IModel<PropertyRow> model) {
		super(id, model);

		if (model.getObject().isResourceReference()) {
			constraintType.setObject(ConstraintType.RESOURCE);
		}

		
		Form<?> form = new Form<Void>("form");
		addComponents(model, form);
		
		add(form);
		// final IModel<Boolean> isResourceReferenceModel =
		// Model.of(model.getObject().isResourceReference());
		//
		// final ClassPickerField resourceConstraint =
		// new ClassPickerField("resourceConstraint", new
		// PropertyModel<ResourceID>(model, "resourceConstraint"));
		// //
		// resourceConstraint.add(visibleIf(isTrue(isResourceReferenceModel)));
		// resourceConstraint.setVisible(model.getObject().isResourceReference());
		// add(resourceConstraint);
		//
		// MarkupContainer literalConstraints = new
		// TextField("literalConstraint", new PropertyModel<ResourceID>(model,
		// "literalConstraint"));
		// literalConstraints.setVisible(model.getObject().isResourceReference());
		// add(literalConstraints);
		// // .add(visibleIf(isFalse(isResourceReferenceModel))));
		setOutputMarkupId(true);
	}

	// ------------------------------------------------------
	
	private void addComponents(final IModel<PropertyRow> model, Form<?> form) {
		addClassPicker(model,form);
		addLiteralConstraintTextField(model,form);
		addConstraintTypeSwitch(form);
	}

	/**
	 * Adds a {@link AjaxLink} to switch between Literal and Resource Constraints.
	 */
	private void addConstraintTypeSwitch(Form<?> form) {
		AjaxLink<String> switchToLiteralLink = new AjaxLink<String>("switchToLiteral") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				updateConstraintType();
				RBAjaxTarget.add(ConstraintsEditorPanel.this);
			}
		};
		switchToLiteralLink.add(visibleIf(areEqual(constraintType, ConstraintType.LITERAL)));
		switchToLiteralLink.add(new Label("switchToLiteral", new ResourceModel("switch-to-resource")));

		AjaxLink<String> switchToResourceLink = new AjaxLink<String>("switchToResource") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				updateConstraintType();
				RBAjaxTarget.add(ConstraintsEditorPanel.this);
			}
		};
		switchToResourceLink.add(visibleIf(areEqual(constraintType, ConstraintType.RESOURCE)));
		switchToResourceLink.add(new Label("switchToResource", new ResourceModel("switch-to-literal")));
		
		form.add(switchToLiteralLink);
		form.add(switchToResourceLink);
	}

	/**
	 * Adds a {@link TextField} with {@link AutoCompleteBehavior} for private and public literal-constraints.
	 */
	private void addLiteralConstraintTextField(final IModel<PropertyRow> model, Form<?> form) {
		final TextField<String> literalConstraint = new TextField<String>("literalConstraint",
				new PropertyModel<String>(model, "literalConstraint"));
		literalConstraint.add(visibleIf(areEqual(constraintType, ConstraintType.LITERAL)));
		form.add(literalConstraint);
	}

	/**
	 * Adds a {@link ClassPickerField} for resource-constraints.
	 */
	private void addClassPicker(final IModel<PropertyRow> model, Form<?> form) {
		final ClassPickerField resourceConstraint = new ClassPickerField("resourceConstraint",
				new PropertyModel<ResourceID>(model, "resourceConstraint"));
		resourceConstraint.add(visibleIf(areEqual(constraintType, ConstraintType.RESOURCE)));
		form.add(resourceConstraint);
	}

	private void updateConstraintType() {
		if (ConstraintType.RESOURCE.name().equals(constraintType.getObject().name())) {
			constraintType.setObject(ConstraintType.LITERAL);
		} else {
			constraintType.setObject(ConstraintType.RESOURCE);
		}
	}
	
	// ------------------------------------------------------
	
	/**
	 * Enum for classifying if the {@link Constraint} is of a resource-reference or a literal.
	 */
	private enum ConstraintType {
		LITERAL, RESOURCE
	}
}
