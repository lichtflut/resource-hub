/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.listview;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;

import scala.actors.threadpool.Arrays;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.conversion.RBFieldConverter;
import de.lichtflut.rb.webck.models.RBFieldsListModel;

/**
 * <p>
 *  List of RBEntities.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityListPanel extends Panel {
	
	private static final String ACTION = "action";
	
	private final RBFieldConverter converter = new RBFieldConverter();
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id
	 * @param model
	 */
	public EntityListPanel(final String id, final IModel<List<RBEntity>> model, final ColumnConfiguration config) {
		super(id, model);
		
		setOutputMarkupId(true);
		
		final ListView<RBEntity> rows = createRows(model, config); 
		add(rows);
	}

	// -- ACTION EVENTS -----------------------------------
	
	/**
	 * Handler for delete.
	 * @param entity The entity.
	 * @param target The ajax request target.
	 */
	protected void onDelete(final RBEntity entity, final AjaxRequestTarget target) {}
	
	/**
	 * Handler for delete.
	 * @param entity The entity.
	 * @param target The ajax request target.
	 */
	protected void onView(final RBEntity entity, final AjaxRequestTarget target) {}
	
	/**
	 * Handler for delete.
	 * @param entity The entity.
	 * @param target The ajax request target.
	 */
	protected void onEdit(final RBEntity entity, final AjaxRequestTarget target) {}
	
	/**
	 * Handler for delete.
	 * @param entity The entity.
	 * @param target The ajax request target.
	 * @param action The custom action.
	 */
	protected void onAction(final RBEntity entity, final AjaxRequestTarget target, final String action) {}
	
	// ----------------------------------------------------
	
	protected Component createViewAction(final String componentId, final RBEntity entity) {
		return new ActionLink(componentId, new ResourceModel("action.view")) {
			public void onClick(final AjaxRequestTarget target) {
				onView(entity, target);
			}
		}; 	
	}
	
	protected Component createEditAction(final String componentId, final RBEntity entity) {
		return new ActionLink(componentId, new ResourceModel("action.edit")) {
			public void onClick(final AjaxRequestTarget target) {
				onEdit(entity, target);
			}
		}; 
	}	
	
	protected Component createDeleteAction(final String componentId, final RBEntity entity) {
		return new ActionLink(componentId, new ResourceModel("action.delete")) {
			public void onClick(final AjaxRequestTarget target) {
				onDelete(entity, target);
			}
		}; 	
	}
	
	protected Component createCustomAction(final String componentId, final RBEntity entity, final String action) {
		return new ActionLink(componentId, new ResourceModel("action.custom-" + action)) {
			public void onClick(final AjaxRequestTarget target) {
				onAction(entity, target, action);
			}
		}; 	
	}
	
	// -- OUTPUT ------------------------------------------
	
	protected String toString(final RBField field, final Locale locale) {
		return converter.convertToString(field, locale);
	}
	
	protected Component createCell(final IModel<RBField> model) {
		final RBField field = model.getObject();
		final String rep = toString(field, RequestCycle.get().getRequest().getLocale());
		return new Label("content", rep);
	}
	
	// ----------------------------------------------------
	
	private ListView<RBEntity> createRows(final IModel<List<RBEntity>> model, final ColumnConfiguration config) {
		return new ListView<RBEntity>("rows", model) {
			@Override
			protected void populateItem(final ListItem<RBEntity> item) {
				item.add(createCells(new RBFieldsListModel(item.getModel())));
				item.add(createActions(item.getModelObject(), config.getActions()));
			}
		};
	}
	
	private ListView<RBField> createCells(final RBFieldsListModel model) {
		final ListView<RBField> columns = new ListView<RBField>("cells", model) {
			@Override
			protected void populateItem(final ListItem<RBField> item) {
				item.add(createCell(item.getModel()));
			}
		}; 
		return columns;
	}
	
	@SuppressWarnings("unchecked")
	private Component createActions(final RBEntity entity, final String... actions) {
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
