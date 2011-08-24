/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.RepeatingView;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.behaviors.DatePickerBehavior;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.models.NewGenericResourceModel;

/**
 * This field displays a String in a simple {@link TextField}.
 *
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
class CKTextField extends CKComponent {

	private IRBField field;
	private WebMarkupContainer container;
	private RepeatingView view;

	// ------------------------------------------------------------

	/**
	 * Constructor.
	 *
	 * @param id - wicket:id
	 * @param field -
	 */
	@SuppressWarnings({ "rawtypes" })
	public CKTextField(final String id, final IRBField field) {
		super(id);
		this.field = field;
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		view = new RepeatingView("repeatingView");
		view.setOutputMarkupId(true);
		for (Object o : field.getFieldValues()) {
			view.add(createTextField(o));
		}
		container.add(view);
		add(container);
		add(new AddValueAjaxButton("button", field) {
			@Override
			public void addField(final AjaxRequestTarget target, final Form<?> form) {
				TextField textField = createTextField("");
				textField.setOutputMarkupId(true);
				view.add(textField);
				target.add(container);
				target.focusComponent(textField);
			}
		});
	}

	// ------------------------------------------------------------

	/**
	 * Creates a {@link TextField} with appropriate {@link NewGenericResourceModel} and value.
	 * @param value - Object to be displayed in {@link TextField}
	 * @return - instance of {@link TextField}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TextField createTextField(final Object value) {
		TextField textField;
		switch (field.getDataType()) {
			case DATE:
				textField = new TextField(view.newChildId(),
						new NewGenericResourceModel(field, value));
				textField.add(new DatePickerBehavior());
				textField.setType(Date.class);
			break;
			case INTEGER:
				textField = new TextField(view.newChildId(),
						new NewGenericResourceModel(field, value));
				textField.setType(Integer.class);
				break;
			default:
				textField = new TextField<Integer>(view.newChildId(),
						new NewGenericResourceModel(field, value));
				textField.setType(String.class);
			break;
		}
		return textField;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		// TODO Auto-generated method stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

}
