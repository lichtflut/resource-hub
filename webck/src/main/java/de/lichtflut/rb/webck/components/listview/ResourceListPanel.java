/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.listview;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.ResourceField;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.components.entity.quickinfo.QuickInfoPopUpPanel;
import de.lichtflut.rb.webck.conversion.SemanticNodesRenderer;
import de.lichtflut.rb.webck.models.fields.UndeclaredFieldsListModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;

/**
 * <p>
 * Displays a list of {@link ResourceNode}s.
 * </p>
 * 
 * <p>
 * Created Dec 15, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class ResourceListPanel extends Panel {

	private static final String ACTION = "action";

	private final SemanticNodesRenderer renderer = new SemanticNodesRenderer();

	@SpringBean
	private EntityManager entityManager;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * 
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
	 * 
	 * @param entity The entity.
	 * @param target The ajax request target.
	 */
	protected void onDelete(final ResourceNode entity, final AjaxRequestTarget target) {
	}

	/**
	 * Handler for delete.
	 * 
	 * @param entity The entity.
	 * @param target The ajax request target.
	 */
	protected void onView(final ResourceNode entity, final AjaxRequestTarget target) {
	}

	/**
	 * Handler for delete.
	 * 
	 * @param entity The entity.
	 * @param target The ajax request target.
	 */
	protected void onEdit(final ResourceNode entity, final AjaxRequestTarget target) {
	}

	/**
	 * Handler for delete.
	 * 
	 * @param entity The entity.
	 * @param target The ajax request target.
	 * @param action The custom action.
	 */
	protected void onAction(final ResourceNode entity, final AjaxRequestTarget target, final String action) {
	}

	// ----------------------------------------------------

	protected Component createViewAction(final String componentId, final ResourceNode entity) {
		return new ActionLink(componentId, new ResourceModel("action.view")) {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				onView(entity, target);
			}
		}.setLinkCssClass("action-view");
	}

	protected Component createEditAction(final String componentId, final ResourceNode entity) {
		return new ActionLink(componentId, new ResourceModel("action.edit")) {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				onEdit(entity, target);
			}
		}.setLinkCssClass("action-edit");
	}

	protected Component createDeleteAction(final String componentId, final ResourceNode entity) {
		return new ActionLink(componentId, new ResourceModel("action.delete")) {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				onDelete(entity, target);
			}
		}.needsConfirmation(new ResourceModel("global.message.delete-confirmation")).setLinkCssClass("action-delete");
	}

	protected Component createCustomAction(final String componentId, final ResourceNode entity, final String action) {
		return new ActionLink(componentId, new ResourceModel("action.custom-" + action)) {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				onAction(entity, target, action);
			}
		}.setLinkCssClass("action-custom action-custom-" + action);
	}

	// -- OUTPUT ------------------------------------------

	protected String toString(final Collection<SemanticNode> nodes, final Locale locale) {
		return renderer.render(nodes, locale);
	}

	protected String getDisplayValue(final IModel<ResourceField> model) {
        return toString(model.getObject().getValues(), getLocale());
	}

	/**
	 * Defines when and how a info-pop-up shall appear.<br>
	 * Default:
	 * <ul>
	 * <li>appears on: onMouseEnter</li>
	 * <li>disappears on: onMouseLeave</li>
	 * <li>show {@link QuickInfoPopUpPanel}</li>
	 * </ul>
	 * 
	 * @param parent parent component for the pop-up
	 * @param popup component that will appear on event
	 */
	protected void addQuickInfoBehavior(final Component parent, final Component popup) {
		popup.setVisible(false);

		parent.setOutputMarkupId(true);
		parent.add(new AjaxEventBehavior("onMouseEnter") {
			@Override
			protected void onEvent(final AjaxRequestTarget target) {
				if(!popup.isVisible()){
					popup.setVisible(true);
					target.add(parent);
				}
			}
		});

		parent.add(new AjaxEventBehavior("onMouseLeave") {
			@Override
			protected void onEvent(final AjaxRequestTarget target) {
				if(popup.isVisible()){
					popup.setVisible(false);
					target.add(parent);
				}
			}
		});
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
        return new ListView<ResourceNode>("rows", model) {
            @Override
            protected void populateItem(final ListItem<ResourceNode> item) {
                final ColumnConfiguration config = configModel.getObject();
                item.add(createCells(new UndeclaredFieldsListModel(item.getModel(), config), item.getModelObject()));
                item.add(createActions(item.getModelObject(), config.getActions()));
            }
        };
	}

	private WebMarkupContainer createCells(final IModel<List<ResourceField>> model, final ResourceNode entityNode) {
        return new ListView<ResourceField>("cells", model) {
            @Override
            protected void populateItem(final ListItem<ResourceField> item) {
                item.add(createQIComponent(entityNode, item));
            }


        };
	}

	private Component createActions(final ResourceNode entity, final String... actions) {
        return new ListView<String>("actions", Arrays.asList(actions)) {
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
	}

    /**
     * Create a container that contains
     * <ul>
     * <li>child component</li>
     * <li>pop-up component</li>
     * </ul>
     * @param entityNode represents a RBEntity
     * @param item listItem
     * @return the container component
     */
    private WebMarkupContainer createQIComponent(final ResourceNode entityNode, final ListItem<ResourceField> item) {
        WebMarkupContainer container = new WebMarkupContainer("container");
        Component placeholder = new WebMarkupContainer("quickInfo");
        Label label = new Label("content",  getDisplayValue(item.getModel()));

        if(RDFS.LABEL.equals(item.getModelObject().getPredicate())){
            IModel<RBEntity> model = new Model<RBEntity>(entityManager.find(entityNode));
            ListModel<RBField> listModel = new ListModel<RBField>(model.getObject().getQuickInfo());
            placeholder = new QuickInfoPopUpPanel("quickInfo", listModel,
                    new Model<String>(new ResourceLabelModel(entityNode).getObject()));
        }

        container.add(label);
        container.add(placeholder);

        addQuickInfoBehavior(container, placeholder);
        return container;
    }

}
