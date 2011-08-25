package de.lichtflut.rb.web.mockPages;

import java.io.Serializable;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.web.components.IFeedbackContainerProvider;
import de.lichtflut.rb.web.models.NewGenericResourceModel;

@SuppressWarnings("serial")
public class FieldSet extends Panel {

	private RepeatingView view;
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field -
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FieldSet(final String id, final IRBField field) {
		super(id);
		setOutputMarkupId(true);
		view = new RepeatingView("valueField");
		for (Object o : field.getFieldValues()) {
			view.add(new TextField(view.newChildId(),new NewGenericResourceModel(field, o), getClass(field.getDataType())));
		}

		add(new Label("label", field.getLabel()));
		add(view);
		add(new AjaxButton("more") {

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				view.add(new TextField(view.newChildId(), new NewGenericResourceModel(field, ""),
						FieldSet.this.getClass(field.getDataType())));
				target.add(FieldSet.this);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				onSubmit(target, form);
				target.add(findParent(IFeedbackContainerProvider.class).getFeedbackContainer());
			}
			
		});
	}

	private Class getClass(ElementaryDataType type) {
		switch (type) {
		case DATE:
			return Date.class;
		case INTEGER:
			return Integer.class;	
		case RESOURCE:
			return Object.class;
		default:
			return String.class;
		}
	}
	
}
