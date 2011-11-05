/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.behaviors.DatePickerBehavior;
import de.lichtflut.rb.webck.models.RBFieldModel;

/**
 * This field displays a String in a simple {@link TextField}.
 *
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
class CKTextField extends Panel {

	private RBField field;
	private WebMarkupContainer container;

	// ------------------------------------------------------------

	/**
	 * Constructor.
	 *
	 * @param id - wicket:id
	 * @param field -
	 */
	@SuppressWarnings({ "rawtypes" })
	public CKTextField(final String id, final RBField field) {
		super(id);
		this.field = field;
		this.setOutputMarkupId(true);
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		
		final RepeatingView view = new RepeatingView("repeatingView");
		for(int i=0; i < field.getSlots(); i++) {
			view.add(createTextField(view.newChildId(), i));
		}
		container.add(view);
		add(container);
		add(new AddValueAjaxButton("button", field) {
			@Override
			public void addField(final AjaxRequestTarget target, final Form<?> form) {
				TextField textField = createTextField(view.newChildId(), field.getSlots());
				field.addValue(null);
				view.add(textField);
				target.add(form);
				target.focusComponent(textField);
			}
		});

	}

	// ------------------------------------------------------------

	/**
	 * Creates a {@link TextField} with appropriate {@link RBFieldModel} and value.
	 * @param id The textfields ID.
	 * @param i - marking the occurrence of the displayed value in {@link RBField}
	 * @return - instance of {@link TextField}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TextField createTextField(String id, final int i) {
		final TextField textField;
		switch (field.getDataType()) {
			case DATE:
				textField = new TextField(id,new RBFieldModel(field, i));
				textField.add(new DatePickerBehavior());
				textField.setType(Date.class);
			break;
			case INTEGER:
				textField = new TextField(id, new RBFieldModel(field, i));
				textField.setType(Integer.class);
				break;
			default:
				textField = new TextField(id, new RBFieldModel(field, i));
				textField.setType(String.class);
		}
		textField.setOutputMarkupId(true);
		return textField;
	}
}
