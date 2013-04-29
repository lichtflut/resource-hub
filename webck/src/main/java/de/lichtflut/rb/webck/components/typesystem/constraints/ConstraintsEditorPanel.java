/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.fields.ResourcePickerField;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;

/**
 * 
 * <p>
 * Panel for editing of Constraints.
 * </p>
 * <p>
 * Created May, 2012
 * </p>
 * 
 * @author Ravi Knox
 */
public class ConstraintsEditorPanel extends Panel {

	private IModel<ConstraintType> constraintType;
	private final IModel<Boolean> isPublic;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 */
	public ConstraintsEditorPanel(final String id, final IModel<PropertyRow> model) {
		super(id, model);
		isPublic = Model.of(model.getObject().isConstraintPublic());

		initConstraintType(model);

		Form<?> form = new Form<Void>("form");
		addComponents(model, form);

		add(form);
		setOutputMarkupId(true);
	}

	// ------------------------------------------------------

	private void initConstraintType(final IModel<PropertyRow> model) {
		if (Datatype.RESOURCE.name().equals(model.getObject().getDataType().name())) {
			constraintType = Model.of(ConstraintType.RESOURCE);
		} else {
			constraintType = Model.of(ConstraintType.LITERAL);
		}
	}

	private void addComponents(final IModel<PropertyRow> model, final Form<?> form) {
		addClassPicker(model, form);

		WebMarkupContainer container = new WebMarkupContainer("container");
		addLiteralConstraintComponents(model, container);
		addConstraintPicker(model, container);
		addConstraintTypeSwitch(model, container);
		container.add(visibleIf(not(areEqual(Model.of(Datatype.RESOURCE), model.getObject().getDataType()))));

		form.add(container);
	}

	private void addConstraintTypeSwitch(final IModel<PropertyRow> model, final WebMarkupContainer container) {
		AjaxLink<String> switchToLiteralLink = new AjaxLink<String>("switchToLiteral") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				isPublic.setObject(false);
				updateConstraintType();
				RBAjaxTarget.add(ConstraintsEditorPanel.this);
			}
		};
		AjaxLink<String> switchToResourceLink = new AjaxLink<String>("switchToResource") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				isPublic.setObject(true);
				updateConstraintType();
				RBAjaxTarget.add(ConstraintsEditorPanel.this);
			}
		};
		switchToLiteralLink.add(visibleIf(areEqual(isPublic, true)));
		switchToLiteralLink.add(new Label("switchToLiteral", new ResourceModel("switch-to-literal")));

		switchToResourceLink.add(visibleIf(areEqual(isPublic, false)));
		switchToResourceLink.add(new Label("switchToResource", new ResourceModel("switch-to-resource")));

		container.add(switchToLiteralLink);
		container.add(switchToResourceLink);
	}

	private void addLiteralConstraintComponents(final IModel<PropertyRow> model, final WebMarkupContainer container) {
		final TextField<String> literalConstraint = new TextField<String>("literalConstraint", new PropertyModel<String>(model,
				"literalConstraint"));
		literalConstraint.add(visibleIf(areEqual(isPublic, false)));
		container.add(literalConstraint);
	}

	private void addConstraintPicker(final IModel<PropertyRow> model, final WebMarkupContainer container) {
		ResourcePickerField picker = new ResourcePickerField("constraintPicker",
				new PropertyModel<ResourceID>(model, "resourceConstraint"), RBSchema.PUBLIC_CONSTRAINT);
		picker.add(visibleIf(areEqual(isPublic, true)));
		container.add(picker);
	}

	private void addClassPicker(final IModel<PropertyRow> model, final Form<?> form) {
		final ClassPickerField resourceConstraint = new ClassPickerField("resourceConstraint", new PropertyModel<ResourceID>(model,
				"resourceConstraint"));
		resourceConstraint.add(visibleIf(areEqual(Model.of(Datatype.RESOURCE), model.getObject().getDataType())));
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
