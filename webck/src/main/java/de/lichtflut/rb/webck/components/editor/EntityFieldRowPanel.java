/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.datepicker.DatePicker;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
import de.lichtflut.rb.webck.models.RBFieldValueModel;
import de.lichtflut.rb.webck.models.RBFieldValuesListModel;

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
		
		final RBFieldValuesListModel listModel = new RBFieldValuesListModel(model);
		final ListView<RBFieldValueModel> valueList = new ListView<RBFieldValueModel>("values", listModel) {
			@Override
			protected void populateItem(final ListItem<RBFieldValueModel> item) {
				addValueField(item, model.getObject().getDataType());
			}
		};
		add(valueList);
		
		final AjaxSubmitLink link = new AjaxSubmitLink("addValueLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				getField().addValue(null);
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		
		add(link);
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
			addDateField(item);
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
	
	private void addResourceField(final ListItem<RBFieldValueModel> item) {
		final ResourceID typeConstraint = getTypeConstraint(getField());
		item.add(new EntityPickerField("valuefield", item.getModelObject(), typeConstraint));
	}
	
	private TextField addTextField(final ListItem<RBFieldValueModel> item, Class<?> type) {
		final TextField field = new TextField("valuefield", item.getModelObject());
		field.setType(type);
		item.add(new Fragment("valuefield", "textInput", this).add(field));
		return field;
	}
	
	private TextField addDateField(final ListItem<RBFieldValueModel> item) {
		final DatePicker<Date> field = new DatePicker<Date>("valuefield", item.getModelObject(), Date.class);
		item.add(new Fragment("valuefield", "textInput", this).add(field));
		return field;
	}
	
	private void addBooleanField(ListItem<RBFieldValueModel> item) {
		final CheckBox cb = new CheckBox("valuefield", item.getModelObject());
		item.add(new Fragment("valuefield", "checkbox", this).add(cb));
	}
	
	/**
	 * Extracts the resourceTypeConstraint of this {@link RBField}.
	 * @param field - IRBField
	 * @return the resourceTypeConstraint as an {@link ResourceID}
	 */
	private ResourceID getTypeConstraint(final RBField field) {
		if(field.getDataType().equals(ElementaryDataType.RESOURCE)){
			for (Constraint c : field.getConstraints()) {
				if(c.isResourceTypeConstraint()){
					return c.getResourceTypeConstraint().asResource();
				}
			}
		}
		return null;
	}
	
	
	// ----------------------------------------------------
	
	private RBField getField() {
		return (RBField) getDefaultModelObject();
	}

}
