/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.ck.components.ResourcePicker;
import de.lichtflut.rb.web.models.NewGenericResourceModel;


/**
 * [TODO Insert description here.
 *
 * Created: Aug 30, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class ResourceField extends Panel {

	private IRBField field;
	private RepeatingView view;
	private int index = 0;
	/**
	 * Constrcutor.
	 * @param id - wicket:id
	 * @param field - {@link IRBField} to be displayed
	 */
	public ResourceField(final String id,final IRBField field) {
		super(id);
		this.field = field;
		WebMarkupContainer container = new WebMarkupContainer("container");
		view = new RepeatingView("repeatingView");
		while(index < field.getFieldValues().size()){
			view.add(createResourcePicker(index++));
		}
		container.add(view);
		add(container);
		add(new AddValueAjaxButton("button", field) {
			@Override
			public void addField(final AjaxRequestTarget target, final Form<?> form) {
				form.add(createResourcePicker(index++));
				target.add(form);
				target.focusComponent(form);
			}
		});
	}

	/**
	 * Creates a {@link ResourcePicker} with appropriate {@link NewGenericResourceModel} and value.
	 * @param i - marking the occurence of the displayed value in {@link IRBField}
	 * @return - instance of {@link ResourcePicker}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ResourcePicker createResourcePicker(final int i){
		ResourcePicker picker = new ResourcePicker(view.newChildId(), new NewGenericResourceModel(field, i)){
			@Override
			public RBServiceProvider getServiceProvider() {
				return null;
			}
			@Override
			public INewRBServiceProvider getNewServiceProvider() {
				return getNewServiceProvider();
			}
			@Override
			public CKComponent setViewMode(final ViewMode mode) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		picker.setOutputMarkupId(true);
		return picker;
	}
}
