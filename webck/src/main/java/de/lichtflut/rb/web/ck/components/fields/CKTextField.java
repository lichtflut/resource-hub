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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.behaviors.DatePickerBehavior;
import de.lichtflut.rb.web.ck.components.CKComponent;

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
	private ListView<Object> listView;
	private WebMarkupContainer container;
	/**
	 * Constructor.
	 *
	 * @param id - wicket:id
	 * @param field -
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CKTextField(final String id, final IRBField field) {
		super(id);
		this.field = field;
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		final IModel<List<Object>> listModel = new ListModel(field.getFieldValues());

		if (listModel.getObject().size() == 0) {
			// Display at least one textfield.
			listModel.getObject().add("");
		}
		container.add(createListView(listModel));
		container.add(new AddValueAjaxButton("button", field) {
			@Override
			public void addField(final AjaxRequestTarget target, final Form<?> form) {
				TextField field = createTextField(listModel, new ListItem("sdf", 44));
				field.setOutputMarkupId(true);
				listView.getModelObject().add(field);
				form.add(listView);
				target.add(listView.getParent());
				target.focusComponent(field);
			}
		});
		add(container);
	}

	/**
	 * Creates a {@link ListView} containing {@link TextField}.
	 * @param listModel - instance of {@link ListModel}
	 * @return A ListView of TextFields
	 */
	private ListView<Object> createListView(final IModel<List<Object>> listModel) {
		listView = new ListView<Object>("listView",
				listModel) {
			private int actualOccurence = 1;
			@SuppressWarnings({ "rawtypes" })
			@Override
			protected void populateItem(final ListItem<Object> item) {
				 TextField textField = createTextField(listModel, item);
				if(field.getCardinality().getMinOccurs() <= actualOccurence){
					textField.setRequired(true);
				}
				actualOccurence++;
				item.add(textField);
			}
		};
		listView.setReuseItems(true);
		listView.setOutputMarkupId(true);
		return listView;
	}

	/**
	 * @param listModel -
	 * @param item -
	 * @return -
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TextField createTextField(
			final IModel<List<Object>> listModel, final ListItem<Object> item) {
		TextField textField;
		switch (field.getDataType()) {
			case DATE:
				textField = new TextField<Date>("value",
						new PropertyModel(listModel, "" + item.getIndex()));
				textField.add(new DatePickerBehavior());
				textField.setType(Date.class);
			break;
			case INTEGER:
				textField = new TextField<Integer>("value",
						new PropertyModel(listModel, "" + item.getIndex()));
				textField.setType(Integer.class);
				break;
			default:
				textField = new TextField<Integer>("value",
						new PropertyModel(listModel, "" + item.getIndex()));
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
