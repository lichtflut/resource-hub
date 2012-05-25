/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.constraints;

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.*;
import static de.lichtflut.rb.webck.models.ConditionalModel.*;

/**
 * 
 * <p>
 *  Panel for editing of Constraints.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ConstraintsEditorPanel extends Panel {
	
	private IModel<ConstraintType> constraintType = Model.of(ConstraintType.LITERAL);
	
	// ---------------- Constructor -------------------------
	
	/**
	 *  Constructor.
	 */
	public ConstraintsEditorPanel(final String id, final IModel<PropertyRow> model) {
		super(id, model);
		
		setOutputMarkupPlaceholderTag(true);
		setOutputMarkupId(true);
		
		if(model.getObject().isResourceReference()){
			constraintType.setObject(ConstraintType.RESOURCE);
		}
		final ClassPickerField resourceConstraint = 
				new ClassPickerField("resourceConstraint", new PropertyModel<ResourceID>(model, "resourceConstraint"));
			resourceConstraint.add(visibleIf(areEqual(constraintType, ConstraintType.RESOURCE)));
			add(resourceConstraint);
		
		CheckBox checkBox = new CheckBox("constraintSwitch");
		add(checkBox);
		Label switchToLiteralText = new Label("switchToLiteral", Model.of("Switch to literal Constraint"));
		Label switchToResourceText = new Label("switchToResource", Model.of("Switch to resource Constraint"));
		switchToLiteralText.add(visibleIf(areEqual(constraintType, ConstraintType.RESOURCE)));
		switchToResourceText.add(visibleIf(areEqual(constraintType, ConstraintType.LITERAL)));
		add(switchToLiteralText);
		add(switchToResourceText);
//		final IModel<Boolean> isResourceReferenceModel = Model.of(model.getObject().isResourceReference());
//
//		final ClassPickerField resourceConstraint = 
//			new ClassPickerField("resourceConstraint", new PropertyModel<ResourceID>(model, "resourceConstraint"));
////		resourceConstraint.add(visibleIf(isTrue(isResourceReferenceModel)));
//		resourceConstraint.setVisible(model.getObject().isResourceReference());
//		add(resourceConstraint);
//		
//		MarkupContainer literalConstraints = new TextField("literalConstraint", new PropertyModel<ResourceID>(model, "literalConstraint"));
//		literalConstraints.setVisible(model.getObject().isResourceReference());
//		add(literalConstraints);
////		.add(visibleIf(isFalse(isResourceReferenceModel))));
	}
	
	private enum ConstraintType{
		LITERAL,
		RESOURCE
	}
}
