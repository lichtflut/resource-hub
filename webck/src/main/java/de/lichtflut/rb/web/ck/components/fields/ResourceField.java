/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.RepeatingView;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
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
public abstract class ResourceField extends CKComponent {

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
		buildComponent();
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
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
			public INewRBServiceProvider getServiceProvider() {
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
}
