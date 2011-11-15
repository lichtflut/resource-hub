/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.behaviors.DatePickerBehavior;
import de.lichtflut.rb.webck.components.fields.ResourcePickerField;
import de.lichtflut.rb.webck.models.RBFieldListModel;
import de.lichtflut.rb.webck.models.RBFieldModel;

/**
 * <p>
 *  Panel containing one field of a entity, which consists of
 *  <ul>
 *  	<li> a label </li>
 *  	<li> one or more values </li>
 *  </ul>
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EntityFieldRowPanel extends Panel {

	/**
	 * @param id
	 * @param model
	 */
	public EntityFieldRowPanel(final String id, final IModel<RBField> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		add(new Label("label", new PropertyModel<String>(model, "label")));
		
		final RBFieldListModel listModel = new RBFieldListModel(model);
		final ListView<RBFieldModel> valueList = new ListView<RBFieldModel>("values", listModel) {
			@Override
			protected void populateItem(final ListItem<RBFieldModel> item) {
				addValueField(item, model.getObject().getDataType());
			}
		};
		add(valueList);
	}
	
	// -----------------------------------------------------
	
	/**
	 * @param item
	 * @param dataType
	 */
	protected void addValueField(final ListItem<RBFieldModel> item, final ElementaryDataType dataType) {
		switch(dataType) {
		case BOOLEAN:
			addBooleanField(item);
			break;
		case RESOURCE:
			addResourceField(item);
			break;
		case DATE:
			addTextField(item, Date.class).add(new DatePickerBehavior());
			break;
		case INTEGER:
			addTextField(item, Integer.class);
			break;
		case DECIMAL:
			addTextField(item, BigDecimal.class);
			break;
		case STRING:
			addTextField(item, String.class);
			break;
		}
	}
	
	private void addResourceField(final ListItem<RBFieldModel> item) {
		item.add(new ResourcePickerField("valuefield", item.getModelObject()));
	}
	
	private TextField addTextField(final ListItem<RBFieldModel> item, Class<?> type) {
		final TextField field = new TextField("valuefield", item.getModelObject());
		field.setType(type);
		item.add(new Fragment("valuefield", "textInput", this).add(field));
		return field;
	}
	
	private void addBooleanField(ListItem<RBFieldModel> item) {
		final CheckBox cb = new CheckBox("valuefield", item.getModelObject());
		item.add(new Fragment("valuefield", "checkbox", this).add(cb));
	}

}
