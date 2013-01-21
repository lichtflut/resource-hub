/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.fields.FilePreviewLink;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.HTMLSafeModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.entity.RBEntityLabelModel;
import de.lichtflut.rb.webck.models.fields.FieldLabelModel;
import de.lichtflut.rb.webck.models.fields.RBFieldLabelCssClassModel;
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

		final WebMarkupContainer labelContainer = new WebMarkupContainer("labelContainer");
		labelContainer.add(CssModifier.appendClass(new RBFieldLabelCssClassModel(model)));
		final Label label = new Label("label", new FieldLabelModel(model));
		labelContainer.add(label);
		add(labelContainer);

		final RBFieldValuesListModel listModel = new RBFieldValuesListModel(model);
		final ListView<RBFieldValueModel> valueList = new ListView<RBFieldValueModel>("values", listModel) {
			@Override
			protected void populateItem(final ListItem<RBFieldValueModel> item) {
				RBFieldValueModel model = item.getModelObject();
				Component component = addValueField(item, model.getField().getDataType());
				if (model.getFieldValue().isInherited()) {
					component.add(CssModifier.appendClass("inherited"));
					component.add(TitleModifier.title(new ResourceModel("label.property-is-inherited")));
				}
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
	protected Component addValueField(final ListItem<RBFieldValueModel> item, final Datatype dataType) {
		switch(dataType) {
			case BOOLEAN:
				return addBooleanField(item);
			case RESOURCE:
				return addResourceField(item);
			case DATE:
				return addTextOutput(item, Date.class);
			case INTEGER:
				return addTextOutput(item, Integer.class);
			case DECIMAL:
				return addTextOutput(item, BigDecimal.class);
			case STRING:
			case TEXT:
				return addTextOutput(item, String.class);
			case RICH_TEXT:
				return addHTMLOutput(item);
			case URI:
				return addExternalLink(item);
			case FILE:
				return addRepoLink(item);
			default:
				throw new NotYetImplementedException("No display-component specified for datatype: " + dataType);
		}
	}

	private Component addResourceField(final ListItem<RBFieldValueModel> item) {
		final ResourceID ref = getResourceID(item.getModelObject());
		if (ref != null) {
			final CrossLink link = new CrossLink("link", getUrlTo(ref).toString());
			link.add(new Label("label", getLabelModel(item.getModelObject())));
			item.add(new Fragment("valuefield", "referenceLink", this).add(link));
			return link;
		} else {
			return addTextOutput(item, ResourceID.class);
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

	private Label addHTMLOutput(final ListItem<RBFieldValueModel> item) {
		final Label field = new Label("valuefield", new HTMLSafeModel(stringModel(item.getModelObject())));
		field.setEscapeModelStrings(false);
		item.add(new Fragment("valuefield", "textOutput", this).add(field));
		return field;
	}

	private ExternalLink addExternalLink(final ListItem<RBFieldValueModel> item) {
		IModel<String> linkModel = stringModel(item.getModelObject());
		ExternalLink link = new ExternalLink("target", linkModel, linkModel);
		link.add(new AttributeModifier("target", "_blank"));
		item.add(new Fragment("valuefield", "link", this).add(link));
		return link;
	}

	private FilePreviewLink addRepoLink(final ListItem<RBFieldValueModel> item) {
		FilePreviewLink filePreviewPanel = new FilePreviewLink("previewPanel", stringModel(item.getModelObject()));
		item.add(new Fragment("valuefield", "filePreview", this).add(filePreviewPanel));
		return filePreviewPanel;
	}

	private Label addBooleanField(final ListItem<RBFieldValueModel> item) {
		String label = "no";
		if (Boolean.TRUE.equals(item.getModelObject().getObject())) {
			label = "yes";
		}
		final Label field = new Label("valuefield", label);
		item.add(new Fragment("valuefield", "textOutput", this).add(field));
		return field;
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

		@Override
		public boolean isFulfilled() {
			return !getObject().getValues().isEmpty();
		}

	}

	private IModel<String> stringModel(final IModel<Object> model) {
		return new DerivedModel<String, Object>(model) {
			@Override
			protected String derive(final Object obj) {
				return obj.toString();
			}
		};
	}

}
