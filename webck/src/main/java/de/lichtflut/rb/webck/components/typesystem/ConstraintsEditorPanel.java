/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.List;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.webck.components.fields.ClassPickerField;
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
	
	/**
	 *  Constructor.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ConstraintsEditorPanel(final String id, final IModel<PropertyRow> model) {
		super(id, model);
		
		setOutputMarkupPlaceholderTag(true);
		setOutputMarkupId(true);
		
		final IModel<Boolean> isResourceReferenceModel = new PropertyModel(model, "isResourceReference");

		final ClassPickerField resourceConstraint = 
			new ClassPickerField("resourceConstraint", new PropertyModel<ResourceID>(model, "resourceConstraint"));
		resourceConstraint.add(visibleIf(isTrue(isResourceReferenceModel)));
		add(resourceConstraint);
		
		final IModel<List<Constraint>> literalConstraintsModel = 
			new PropertyModel<List<Constraint>>(model, "literalConstraints");
		add(new ListView<Constraint>("literalConstraintsList", literalConstraintsModel) {
			protected void populateItem(final ListItem<Constraint> item) {
				item.add(new TextField("literalConstraint", item.getModel()));
			}
		}.add(visibleIf(isFalse(isResourceReferenceModel))));
	}
	
}
