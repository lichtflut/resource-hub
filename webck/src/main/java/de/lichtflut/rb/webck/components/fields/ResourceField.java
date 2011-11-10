/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.webck.components.CKComponent;
import de.lichtflut.rb.webck.models.RBFieldModel;


/**
 * [TODO Insert description here.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public abstract class ResourceField extends CKComponent {

	private RBField field;
	private RepeatingView view;
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field - {@link RBField} to be displayed
	 */
	public ResourceField(final String id,final RBField field) {
		super(id);
		this.field = field;
		buildComponent();
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		WebMarkupContainer container = new WebMarkupContainer("container");
		view = new RepeatingView("repeatingView");
		for(int i=0; i < field.getSlots(); i++) {
			view.add(createResourcePicker(i));
		}
		container.add(view);
		container.add(new AddValueAjaxButton("button", field) {
			@Override
			public void addField(final AjaxRequestTarget target, final Form<?> form) {
				EntityPickerField picker = createResourcePicker(field.getSlots());
				picker.setOutputMarkupId(true);
				view.add(picker);
				target.add(form);
				target.focusComponent(picker);
			}
		});
		add(container);
	}

	/**
	 * Creates a resource picker field with appropriate {@link RBFieldModel} and value.
	 * @param i - marking the occurrence of the displayed value in {@link RBField}
	 * @return - instance of resource picker
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private EntityPickerField createResourcePicker(final int i) {
		final ResourceID typeConstraint = getTypeConstraint(field);
		final RBFieldModel model = new RBFieldModel(field, i);
		final EntityPickerField rp = new EntityPickerField(view.newChildId(), model, typeConstraint);
		return rp;
	}

	/**
	 * Extracts the resourceTypeConstraint of this {@link RBField}.
	 * @param field - IRBField
	 * @return the resourceTypeConstraint as an {@link ResourceID}
	 */
	private ResourceID getTypeConstraint(final RBField field) {
		if(field.getDataType().equals(ElementaryDataType.RESOURCE)){
			for (Constraint c : field.getConstraints()) {
				if(c.isResourceTypeConstraint()){
					return c.getResourceTypeConstraint().asResource();
				}
			}
		}
		return null;
	}
}
