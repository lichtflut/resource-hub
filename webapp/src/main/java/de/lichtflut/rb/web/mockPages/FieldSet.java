package de.lichtflut.rb.web.mockPages;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.web.components.IFeedbackContainerProvider;
import de.lichtflut.rb.web.models.NewGenericResourceModel;
/**
 * [TODO Insert description here.
 *
 * Created: Aug 26, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class FieldSet extends Panel {

	private RepeatingView view;
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field -
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public FieldSet(final String id, final RBField field) {
		super(id);
		setOutputMarkupId(true);
		view = new RepeatingView("valueField");
		for (Object o : field.getValues()) {
			view.add(new TextField(view.newChildId(),new NewGenericResourceModel(field, 0), getClass(field.getDataType())));
		}

		add(new Label("label", field.getLabel()));
		add(view);
		add(new AjaxButton("more") {

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				view.add(new TextField(view.newChildId(), new NewGenericResourceModel(field, 0),
						FieldSet.this.getClass(field.getDataType())));
				target.add(FieldSet.this);
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				onSubmit(target, form);
				target.add(findParent(IFeedbackContainerProvider.class).getFeedbackContainer());
			}
		});
	}

	/**
	 * .
	 * @param type -
	 * @return -
	 */
	@SuppressWarnings("rawtypes")
	private Class getClass(final ElementaryDataType type) {
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
