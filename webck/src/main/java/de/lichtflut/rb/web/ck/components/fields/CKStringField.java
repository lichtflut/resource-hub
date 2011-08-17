/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.components.CKComponent;

/**
 * This field displays a String in a simple {@link TextField}.
 *
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
class CKStringField extends CKComponent {

	/**
	 * Constructor.
	 *
	 * @param id - wicket:id
	 * @param field -
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CKStringField(final String id, final IRBField field) {
		super(id);
		IModel<List<String>> listModel = new ListModel(field.getFieldValues());
//		setDefaultModel(listModel);

		if (listModel.getObject().size() == 0) {
			// Display at least one textfield.
			listModel.getObject().add("");
		}
		add(new Label("label", new Model(field.getLabel())));
		add(createListView(listModel));
	}

	/**
	 * Creates a {@link ListView} containing {@link TextField}.
	 * @param listModel - instance of {@link ListModel}
	 * @return A ListView of TextFields
	 */
	private ListView<String> createListView(final IModel<List<String>> listModel) {
		final ListView<String> listView = new ListView<String>("listView",
				listModel) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<String> item) {
				TextField<String> textField = new TextField<String>("value",
			            new PropertyModel<String>(listModel, "" + item.getIndex()));
				textField.setType(String.class);
				item.add(textField);
			}
		};
		listView.setReuseItems(true);
		setOutputMarkupId(true);
		return listView;
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
