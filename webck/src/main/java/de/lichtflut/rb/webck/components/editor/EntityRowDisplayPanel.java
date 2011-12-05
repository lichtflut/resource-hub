/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ElementaryDataType;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.models.FieldLabelModel;
import de.lichtflut.rb.webck.models.RBFieldValueModel;
import de.lichtflut.rb.webck.models.RBFieldValuesListModel;

/**
 * <p>
 *  Panel containing one field of a entity, which consists of
 *  <ul>
 *  	<li> a label </li>
 *  	<li> one or more values </li>
 *  </ul>
 *  for display.
 * </p>
 *
 * <p>
 * 	Created Nov 18, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EntityRowDisplayPanel extends Panel {

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model.
	 */
	public EntityRowDisplayPanel(final String id, final IModel<RBField> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		add(new Label("label", new FieldLabelModel(model)));
		
		final RBFieldValuesListModel listModel = new RBFieldValuesListModel(model);
		final ListView<RBFieldValueModel> valueList = new ListView<RBFieldValueModel>("values", listModel) {
			@Override
			protected void populateItem(final ListItem<RBFieldValueModel> item) {
				addValueField(item, model.getObject().getDataType());
			}
		};
		valueList.setReuseItems(true);
		add(valueList);
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param item
	 * @param dataType
	 */
	protected void addValueField(final ListItem<RBFieldValueModel> item, final ElementaryDataType dataType) {
		switch(dataType) {
		case BOOLEAN:
			addBooleanField(item);
			break;
		case RESOURCE:
			addResourceField(item);
			break;
		case DATE:
			addTextOutput(item, Date.class);
			break;
		case INTEGER:
			addTextOutput(item, Integer.class);
			break;
		case DECIMAL:
			addTextOutput(item, BigDecimal.class);
			break;
		case STRING:
			addTextOutput(item, String.class);
			break;
		}
	}
	
	private void addResourceField(final ListItem<RBFieldValueModel> item) {
		final RBEntityReference ref = (RBEntityReference) item.getModelObject().getObject();
		if (ref != null) {
			final CrossLink link = new CrossLink("link", getUrlTo(ref));
			link.add(new Label("label", Model.of(ref.toString())));
			item.add(new Fragment("valuefield", "referenceLink", this).add(link));
		} else {
			addTextOutput(item, RBEntityReference.class);
		}
	}

	protected CharSequence getUrlTo(final RBEntityReference ref) {
		final IBrowsingHandler handler = findParent(IBrowsingHandler.class);
		if (handler == null) {
			throw new IllegalStateException(getClass().getSimpleName() 
					+ " must be placed placed in a component/page that implements " 
					+ IBrowsingHandler.class);
		}
		final CharSequence url = handler.getUrlToResource(EntityHandle.forID(ref));
		return url;
	}
	
	private Label addTextOutput(final ListItem<RBFieldValueModel> item, Class<?> type) {
		final Label field = new Label("valuefield", item.getModelObject());
		item.add(new Fragment("valuefield", "textOutput", this).add(field));
		return field;
	}
	
	private void addBooleanField(ListItem<RBFieldValueModel> item) {
		final CheckBox cb = new CheckBox("valuefield", item.getModelObject());
		cb.setEnabled(false);
		item.add(new Fragment("valuefield", "checkbox", this).add(cb));
	}
	
}
