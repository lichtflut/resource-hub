/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.HTMLSafeModel;
import de.lichtflut.rb.webck.models.domains.CurrentDomainModel;
import de.lichtflut.rb.webck.models.entity.RBEntityLabelModel;
import de.lichtflut.rb.webck.models.fields.FieldLabelModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValuesListModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;

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
	 * Add a value field to the list item.
	 * @param item The list item.
	 * @param dataType The datatype
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
		case URI:
		case FILE:
			addExternalLink(item);
			break;
		default:
			throw new NotYetImplementedException("No display-component specified for datatype: " + dataType);
		}
	}

	private void addResourceField(final ListItem<RBFieldValueModel> item) {
		final ResourceID ref = getResourceID(item.getModelObject());
		if (ref != null) {
			final CrossLink link = new CrossLink("link", getUrlTo(ref).toString());
			link.add(new Label("label", getLabelModel(item.getModelObject())));
			item.add(new Fragment("valuefield", "referenceLink", this).add(link));
		} else {
			addTextOutput(item, ResourceID.class);
		}
	}

	protected CharSequence getUrlTo(final ResourceID ref) {
		return resourceLinkProvider.getUrlToResource(ref, VisualizationMode.DETAILS, DisplayMode.VIEW);
	}

	private Label addTextOutput(final ListItem<RBFieldValueModel> item, final Class<?> type) {
		final Label field = new Label("valuefield", item.getModelObject());
		item.add(new Fragment("valuefield", "textOutput", this).add(field));
		return field;
	}

	private void addHTMLOutput(final ListItem<RBFieldValueModel> item) {
		@SuppressWarnings("unchecked")
		final Label field = new Label("valuefield", new HTMLSafeModel(item.getModelObject()));
		field.setEscapeModelStrings(false);
		item.add(new Fragment("valuefield", "textOutput", this).add(field));
	}

	private void addExternalLink(final ListItem<RBFieldValueModel> item) {
		IModel<String> hrefModel = new Model<String>(item.getModelObject().getField().getValue(0).toString());
		if(Datatype.FILE.name().equals(item.getModelObject().getField().getDataType().name())){
			String href = hrefModel.getObject() + "?domain=" + new CurrentDomainModel().getObject().getQualifiedName();
			hrefModel.setObject(href);
		}
		ExternalLink link = new ExternalLink("target", hrefModel, getDisplayNameForLink(item));
		item.add(new Fragment("valuefield", "link", this).add(link));
	}

	//	private void addLink(final ListItem<RBFieldValueModel> item) {
	//		@SuppressWarnings("unchecked")
	//		ExternalLink link = new ExternalLink("target", item.getModelObject(), item.getModelObject());
	//		item.add(new Fragment("valuefield", "link", this).add(link));
	//	}

	private void addBooleanField(final ListItem<RBFieldValueModel> item) {
		String label = "no";
		if (Boolean.TRUE.equals(item.getModelObject().getObject())) {
			label = "yes";
		}
		final Label field = new Label("valuefield", label);
		item.add(new Fragment("valuefield", "textOutput", this).add(field));
	}

	private IModel<String> getDisplayNameForLink(final ListItem<RBFieldValueModel> item) {
		@SuppressWarnings("unchecked")
		IModel<String> name = item.getModelObject();
		RBField field = item.getModelObject().getField();
		if(Datatype.FILE.name().equals(field.getDataType().name())) {
			String s = (String) field.getValue(item.getModelObject().getIndex());
			return Model.of(StringUtils.substringAfterLast(s, "/"));
		}
		return name;
	}

	private ResourceID getResourceID(final RBFieldValueModel model) {
		Object object = model.getObject();
		if (object == null) {
			return null;
		} else if (object instanceof ResourceID) {
			return (ResourceID) object;
		} else if (object instanceof RBEntity) {
			RBEntity entity = (RBEntity) object;
			return entity.getID();
		} else {
			throw new IllegalArgumentException("Cannot retrieve resource ID from object of type " + object.getClass());
		}
	}

	private IModel<String> getLabelModel(final RBFieldValueModel model) {
		Object object = model.getObject();
		if (object == null) {
			return null;
		} else if (object instanceof ResourceID) {
			return new ResourceLabelModel((ResourceID) object);
		} else if (object instanceof RBEntity) {
			return new RBEntityLabelModel((RBEntity) object);
		} else {
			throw new IllegalArgumentException("Cannot retrieve resource ID from object of type " + object.getClass());
		}
	}

	// ----------------------------------------------------

	private class NotEmptyCondition extends ConditionalModel<RBField> {

		/**
		 * @param model The model to check.
		 */
		public NotEmptyCondition(final IModel<RBField> model) {
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
