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
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.CKComponent;
import de.lichtflut.rb.webck.components.ResourcePicker;
import de.lichtflut.rb.webck.models.NewGenericResourceModel;


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
	private int index = 0;
	/**
	 * Constrcutor.
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
		while(index < field.getValues().size()){
			view.add(createResourcePicker(index++));
		}
		if(field.getValues().size() <= 0){
			view.add(createResourcePicker(index++));
		}
		container.add(view);
		add(container);
		add(new AddValueAjaxButton("button", field) {
			@Override
			public void addField(final AjaxRequestTarget target, final Form<?> form) {
				ResourcePicker picker = createResourcePicker(index++);
				picker.setOutputMarkupId(true);
				view.add(picker);
				target.add(form);
				target.focusComponent(picker);
			}
		});
	}

	/**
	 * Creates a {@link ResourcePicker} with appropriate {@link NewGenericResourceModel} and value.
	 * @param i - marking the occurence of the displayed value in {@link RBField}
	 * @return - instance of {@link ResourcePicker}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ResourcePicker createResourcePicker(final int i){
		ResourcePicker picker = new ResourcePicker(view.newChildId(), new NewGenericResourceModel(field, i),
				extractTypeConstraint(field)){
			@Override
			public ServiceProvider getServiceProvider() {
				return ResourceField.this.getServiceProvider();
			}
			@Override
			public CKComponent setViewMode(final ViewMode mode) {
				return null;
			}
		};
		picker.setOutputMarkupId(true);
		return picker;
	}

	/**
	 * Extracts the resourceTypeConstraint of this {@link RBField}.
	 * @param field - IRBField
	 * @return the resourceTypeConstraint as an {@link ResourceID}
	 */
	private ResourceID extractTypeConstraint(final RBField field) {
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
