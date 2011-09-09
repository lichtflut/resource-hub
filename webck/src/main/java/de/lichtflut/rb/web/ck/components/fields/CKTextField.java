/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.web.behaviors.DatePickerBehavior;
import de.lichtflut.rb.web.models.NewGenericResourceModel;

/**
 * This field displays a String in a simple {@link TextField}.
 *
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
class CKTextField extends Panel {

	private IRBField field;
	private WebMarkupContainer container;
	private RepeatingView view;
	private List<Object> values;
	private int index = 0;

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
		values = field.getFieldValues();
		this.setOutputMarkupId(true);
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		view = new RepeatingView("repeatingView");
		if (values.isEmpty()) {
			if(field.getDataType().equals(ElementaryDataType.STRING)){
				values.add("");
			}else if(field.getDataType().equals(ElementaryDataType.DATE)){
				values.add(new Date());
			}
		}
		while(index < values.size()){

			view.add(createTextField(index++));
		}
		container.add(view);
		add(container);
		add(new AddValueAjaxButton("button", field) {
			@Override
			public void addField(final AjaxRequestTarget target, final Form<?> form) {
				TextField textField = createTextField(index++);
				textField.setOutputMarkupId(true);
				view.add(textField);
				target.add(form);
				target.focusComponent(textField);
			}
		});

	}

	// ------------------------------------------------------------

	/**
	 * Creates a {@link TextField} with appropriate {@link NewGenericResourceModel} and value.
	 * @param i - marking the occurence of the displayed value in {@link IRBField}
	 * @return - instance of {@link TextField}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TextField createTextField(final int i) {
		TextField textField;
		switch (field.getDataType()) {
			case DATE:
				if (!(values.get(i) instanceof Date)) {
					throw new IllegalStateException("Not a date: " + values.get(i));
				}
				textField = new TextField(view.newChildId(),
						new NewGenericResourceModel(field, i));
				textField.add(new DatePickerBehavior());
				textField.setType(Date.class);
			break;
			case INTEGER:
				if (!(values.get(i) instanceof Integer)) {
					throw new IllegalStateException("Not an integer: " + i);
				}
				textField = new TextField(view.newChildId(),
						new NewGenericResourceModel(field, i));
				textField.setType(Integer.class);
				break;
			default:
				textField = new TextField(view.newChildId(),
						new NewGenericResourceModel(field, i));
				textField.setType(String.class);
			break;
		}
		return textField;
	}
}
