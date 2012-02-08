/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.HTMLSafeModel;
import de.lichtflut.rb.webck.models.fields.FieldLabelModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValuesListModel;

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
@SuppressWarnings("rawtypes")
public class EntityRowDisplayPanel extends Panel {
	
	@SpringBean
	private ResourceLinkProvider resourceLinkProvider;
	
	// ----------------------------------------------------

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
		add(ConditionalBehavior.visibleIf(new NotEmptyCondition(model)));
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param item
	 * @param dataType
	 */
	protected void addValueField(final ListItem<RBFieldValueModel> item, final Datatype dataType) {
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
		case TEXT:
			addTextOutput(item, String.class);
			break;
		case RICH_TEXT:
			addHTMLOutput(item);
			break;
		}
	}

	private void addResourceField(final ListItem<RBFieldValueModel> item) {
		final ResourceID ref = (ResourceID) item.getModelObject().getObject();
		if (ref != null) {
			final CrossLink link = new CrossLink("link", getUrlTo(ref).toString());
			link.add(new Label("label", Model.of(ResourceLabelBuilder.getInstance().getLabel(ref, getLocale()))));
			item.add(new Fragment("valuefield", "referenceLink", this).add(link));
		} else {
			addTextOutput(item, ResourceID.class);
		}
	}

	protected CharSequence getUrlTo(final ResourceID ref) {
		return resourceLinkProvider.getUrlToResource(ref, VisualizationMode.DETAILS, DisplayMode.VIEW);
	}
	
	private Label addTextOutput(final ListItem<RBFieldValueModel> item, Class<?> type) {
		final Label field = new Label("valuefield", item.getModelObject());
		item.add(new Fragment("valuefield", "textOutput", this).add(field));
		return field;
	}
	
	private void addHTMLOutput(ListItem<RBFieldValueModel> item) {
		@SuppressWarnings("unchecked")
		final Label field = new Label("valuefield", new HTMLSafeModel(item.getModelObject()));
		field.setEscapeModelStrings(false);
		item.add(new Fragment("valuefield", "textOutput", this).add(field));
	}
	
	private void addBooleanField(ListItem<RBFieldValueModel> item) {
		String label = "no";
		if (Boolean.TRUE.equals(item.getModelObject().getObject())) {
			label = "yes";
		}
		final Label field = new Label("valuefield", label);
		item.add(new Fragment("valuefield", "textOutput", this).add(field));
	}
	
	// ----------------------------------------------------
	
	private class NotEmptyCondition extends ConditionalModel<RBField> {

		/**
		 * @param model
		 */
		public NotEmptyCondition(IModel<RBField> model) {
			super(model);
		}

		/** 
		* {@inheritDoc}
		*/
		@Override
		public boolean isFulfilled() {
			return !getObject().getValues().isEmpty();
		}
		
	}
	
}
