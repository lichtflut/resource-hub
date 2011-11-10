/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import java.util.List;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import de.lichtflut.rb.core.entity.RBField;

/**
 * This field displays a Boolean as a {@link CheckBox}.
 *
 * Created: Aug 17, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
class CKCheckBoxField extends Panel {


	/**
	 * Constructor.
	 *
	 * @param id - wicket:id
	 * @param field - instance of {@link RBField}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CKCheckBoxField(final String id, final RBField field) {
		super(id);
		IModel<List<Boolean>> listModel = new ListModel(field.getValues());
		add(createListView(listModel));
	}

	// ------------------------------------------------------------

	/**
	 * Creates a {@link ListView} containing {@link CheckBox}.
	 *
	 * @param listModel	- instance of {@link ListModel}
	 * @return A ListView of Checkbox
	 */
	private ListView<Boolean> createListView(
			final IModel<List<Boolean>> listModel) {
		final ListView<Boolean> listView = new ListView<Boolean>("listView") {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			protected void populateItem(final ListItem<Boolean> item) {
				CheckBox checkBox = new CheckBox("value",
						new PropertyModel(listModel, "" + item.getIndex()));
				item.add(checkBox);
			}
		};
		listView.setReuseItems(true);
		setOutputMarkupId(true);
		return listView;
	}

}
