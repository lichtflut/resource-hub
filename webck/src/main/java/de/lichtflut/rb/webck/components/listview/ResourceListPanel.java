/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.listview;

import de.lichtflut.rb.core.entity.ResourceField;
import de.lichtflut.rb.webck.conversion.SemanticNodesRenderer;
import de.lichtflut.rb.webck.models.fields.UndeclaredFieldsListModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 *  Displays a list of {@link ResourceNode}s.
 * </p>
 * 
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceListPanel extends Panel {
	
	private static final String ACTION = "action";
	
	private final SemanticNodesRenderer renderer = new SemanticNodesRenderer();
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param dataModel The model providing the data to display..
	 * @param config The configuration of the table and it's columns.
	 */
	public ResourceListPanel(final String id, final IModel<List<ResourceNode>> dataModel, final IModel<ColumnConfiguration> config) {
		super(id, dataModel);
		
		setOutputMarkupId(true);
		
		add(createHeaders(config));
		
		add(createRows(dataModel, config));
	}

	// -- ACTION EVENTS -----------------------------------
	
	/**
	 * Handler for delete.
	 * @param entity The entity.
	 * @param target The ajax request target.
	 */
	protected void onDelete(final ResourceNode entity, final AjaxRequestTarget target) {}
	
	/**
	 * Handler for delete.
	 * @param entity The entity.
	 * @param target The ajax request target.
	 */
	protected void onView(final ResourceNode entity, final AjaxRequestTarget target) {}
	
	/**
	 * Handler for delete.
	 * @param entity The entity.
	 * @param target The ajax request target.
	 */
	protected void onEdit(final ResourceNode entity, final AjaxRequestTarget target) {}
	
	/**
	 * Handler for delete.
	 * @param entity The entity.
	 * @param target The ajax request target.
	 * @param action The custom action.
	 */
	protected void onAction(final ResourceNode entity, final AjaxRequestTarget target, final String action) {}
	
	// ----------------------------------------------------
	
	protected Component createViewAction(final String componentId, final ResourceNode entity) {
		return new ActionLink(componentId, new ResourceModel("action.view")) {
			public void onClick(final AjaxRequestTarget target) {
				onView(entity, target);
			}
		}.setLinkCssClass("action-view");
	}
	
	protected Component createEditAction(final String componentId, final ResourceNode entity) {
		return new ActionLink(componentId, new ResourceModel("action.edit")) {
			public void onClick(final AjaxRequestTarget target) {
				onEdit(entity, target);
			}
		}.setLinkCssClass("action-edit"); 
	}	
	
	protected Component createDeleteAction(final String componentId, final ResourceNode entity) {
		return new ActionLink(componentId, new ResourceModel("action.delete")) {
			public void onClick(final AjaxRequestTarget target) {
				onDelete(entity, target);
			}
		}.needsConfirmation(new ResourceModel("global.message.delete-confirmation")).setLinkCssClass("action-delete");
	}
	
	protected Component createCustomAction(final String componentId, final ResourceNode entity, final String action) {
		return new ActionLink(componentId, new ResourceModel("action.custom-" + action)) {
			public void onClick(final AjaxRequestTarget target) {
				onAction(entity, target, action);
			}
		}.setLinkCssClass("action-custom action-custom-" +action);	
	}
	
	// -- OUTPUT ------------------------------------------
	
	protected String toString(final Collection<SemanticNode> nodes, final Locale locale) {
		return renderer.render(nodes, locale);
	}
	
	protected Component createCell(final IModel<ResourceField> model) {
		final String rep = toString(model.getObject().getValues(), getLocale());
		return new Label("content", rep);
	}
	
	// ----------------------------------------------------
	
	private ListView<ColumnHeader> createHeaders(final IModel<ColumnConfiguration> configModel) {
		return new ListView<ColumnHeader>("headers", new ColumnHeaderModel(configModel)) {
			@Override
			protected void populateItem(final ListItem<ColumnHeader> item) {
				final ColumnHeader header = item.getModelObject();
				item.add(new Label("header", header.getLabel()));
			}
		};
	}
	
	private ListView<ResourceNode> createRows(final IModel<List<ResourceNode>> model, final IModel<ColumnConfiguration> configModel) {
		final ListView<ResourceNode> listView = new ListView<ResourceNode>("rows", model) {
			@Override
			protected void populateItem(final ListItem<ResourceNode> item) {
				final ColumnConfiguration config = configModel.getObject();
				item.add(createCells(new UndeclaredFieldsListModel(item.getModel(), config)));
				item.add(createActions(item.getModelObject(), config.getActions()));
			}
		};
		return listView;
	}
	
	private ListView<ResourceField> createCells(final IModel<List<ResourceField>> model) {
		final ListView<ResourceField> columns = new ListView<ResourceField>("cells", model) {
			@Override
			protected void populateItem(final ListItem<ResourceField> item) {
				item.add(createCell(item.getModel()));
			}
		}; 
		return columns;
	}
	
	@SuppressWarnings("unchecked")
	private Component createActions(final ResourceNode entity, final String... actions) {
		final ListView<String> columns = new ListView<String>("actions", Arrays.asList(actions)) {
			@Override
			protected void populateItem(final ListItem<String> item) {
				final ListAction action = ListAction.forName(item.getModelObject());
				switch (action) {
				case VIEW:
					item.add(createViewAction(ACTION, entity));
					break;
				case EDIT:
					item.add(createEditAction(ACTION, entity));
					break;
				case DELETE:
					item.add(createDeleteAction(ACTION, entity));
					break;
				default:
					item.add(createCustomAction(ACTION, entity, item.getModelObject()));
					break;
				}
			}
		}; 
		return columns;
	}
	
}
