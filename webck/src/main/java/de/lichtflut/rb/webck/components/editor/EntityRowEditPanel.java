/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.datepicker.DatePicker;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.common.Action;
import de.lichtflut.rb.webck.common.EntityAttributeApplyAction;
import de.lichtflut.rb.webck.components.fields.EntityPickerField;
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
 *  for editing.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EntityRowEditPanel extends Panel {

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model.
	 */
	public EntityRowEditPanel(final String id, final IModel<RBField> model) {
		super(id, model);
		
		setOutputMarkupId(true);
		add(new Label("label", new FieldLabelModel(model)));
		
		final RBFieldValuesListModel listModel = new RBFieldValuesListModel(model);
		final ListView<RBFieldValueModel> valueList = new ListView<RBFieldValueModel>("values", listModel) {
			@Override
			protected void populateItem(final ListItem<RBFieldValueModel> item) {
				addValueField(item, model.getObject().getDataType());
				item.add(createRemoveLink(item.getIndex()));
				item.add(createCreateLink(item.getIndex()));
			}
		};
		valueList.setReuseItems(true);
		add(valueList);
		
		final AjaxSubmitLink link = new AjaxSubmitLink("addValueLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				getField().addValue(null);
				target.add(EntityRowEditPanel.this);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		link.add(new AttributeModifier("title", new ResourceModel("link.title.add-field-value")));
		add(link);
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param item
	 * @param dataType
	 * @return 
	 */
	protected FormComponent<?> addValueField(final ListItem<RBFieldValueModel> item, final ElementaryDataType dataType) {
		switch(dataType) {
		case BOOLEAN:
			return addBooleanField(item);
		case RESOURCE:
			return addResourceField(item);
		case DATE:
			return addDateField(item);
		case INTEGER:
			return addTextField(item, Integer.class);
		case DECIMAL:
			return addTextField(item, BigDecimal.class);
		case STRING:
			return addTextField(item, String.class);
		default:
			throw new NotYetImplementedException("Datatype: " + dataType);
		}
	}
	
	protected AjaxSubmitLink createRemoveLink(final int index) {
		final AjaxSubmitLink link = new AjaxSubmitLink("removeLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				getField().removeSlot(index);
				target.add(EntityRowEditPanel.this);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(EntityRowEditPanel.this);
			}
		};
		link.add(new AttributeModifier("title", new ResourceModel("link.title.remove-field-value")));
		return link;
	}
	
	protected Component createCreateLink(final int index) {
		final AjaxSubmitLink link = new AjaxSubmitLink("createLink") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				final EntityHandle handle = EntityHandle.forType(getTypeConstraint());
				final Action action = new EntityAttributeApplyAction(getField().getPredicate());
				RBWebSession.get().getHistory().createReferencedEntity(handle, action);
				
				target.add((Component)EntityRowEditPanel.this.findParent(IBrowsingHandler.class));
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(EntityRowEditPanel.this);
			}
		};
		
		if (getField().isResourceReference()){
			link.add(new AttributeModifier("title", new ResourceModel("link.title.create-field-value")));	
		} else {
			link.setVisible(false);
		}
		return link;

	}
	
	// ----------------------------------------------------
	
	private EntityPickerField addResourceField(final ListItem<RBFieldValueModel> item) {
		final ResourceID typeConstraint = getTypeConstraint();
		final EntityPickerField field = new EntityPickerField("valuefield", item.getModelObject(), typeConstraint);
		item.add(field);
		return field;
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
	
	private CheckBox addBooleanField(ListItem<RBFieldValueModel> item) {
		final CheckBox cb = new CheckBox("valuefield", item.getModelObject());
		item.add(new Fragment("valuefield", "checkbox", this).add(cb));
		return cb;
	}
	
	// ----------------------------------------------------
	
	/**
	 * Extracts the resourceTypeConstraint of this {@link RBField}.
	 * @param field - IRBField
	 * @return the resourceTypeConstraint as an {@link ResourceID}
	 */
	private ResourceID getTypeConstraint() {
		final RBField field = getField();
		if(field.getDataType().equals(ElementaryDataType.RESOURCE)){
			for (Constraint c : field.getConstraints()) {
				if(c.isResourceTypeConstraint()){
					return c.getResourceTypeConstraint().asResource();
				}
			}
		}
		return null;
	}
	
	private RBField getField() {
		return (RBField) getDefaultModelObject();
	}
	
}
