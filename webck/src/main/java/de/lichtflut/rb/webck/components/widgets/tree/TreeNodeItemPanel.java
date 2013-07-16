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
package de.lichtflut.rb.webck.components.widgets.tree;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.behaviors.CssModifier;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.entity.quickinfo.QuickInfoPanel;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.structure.TreeStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;
import static de.lichtflut.rb.webck.models.ConditionalModel.isTrue;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;
import static de.lichtflut.rb.webck.models.ConditionalModel.or;

/**
 * <p>
 *  Panel displaying an item with it's quick-info, if available.
 * <p>
 *  Additionally it's possible to expand this item and show a list of all sub items.
 * </p>
 * 
 * <p>
 *  Created Nov 29, 2013
 * </p>
 * 
 * @author Oliver Tigges
 */
public class TreeNodeItemPanel extends TypedPanel<ResourceNode> {

    public static final String EVENT_TREE_UPDATE = "TreeNodeItemPanel:event.tree-update";

    private static final Logger LOGGER = LoggerFactory.getLogger(TreeNodeItemPanel.class);

	@SpringBean
	private EntityManager entityManager;

    @SpringBean
    private ResourceLinkProvider resourceLinkProvider;

	private final IModel<Boolean> expanded = new Model<Boolean>(false);

    private final int level;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public TreeNodeItemPanel(final String id, final IModel<ResourceNode> model) {
		this(id, model, 0);
	}

    /**
     * Constructor.
     *
     * @param id The component ID.
     * @param model The model.
     * @param level The level in the tree.
     */
    public TreeNodeItemPanel(final String id, final IModel<ResourceNode> model, final int level) {
        super(id, model);

        this.level = level;

        setOutputMarkupPlaceholderTag(true);

        final IModel<List<ResourceNode>> childrenModel = getChildren(model);

        add(createIndention("indention"));
        add(new Label("label", new ResourceLabelModel(model)));
        add(createExpandLink("expand", childrenModel));
        add(createCollapseLink("collapse"));
        add(createSelectLink("select", model));

        add(createChildrenList("children", childrenModel));
    }

    // ----------------------------------------------------

    /**
     * Expand levels, until maximum nodes are displayed.
     * @param maxNodes Max nodes to be displayed.
     * @return How many nodes are displayed.
     */
    public int expand(int maxNodes) {
        throw new NotYetImplementedException();
    }

    // ----------------------------------------------------

    private Component createIndention(String id) {
        return new Label(id, "+").add(CssModifier.appendStyle("width: " + level * 20 + "px"));
    }

	private Component createExpandLink(final String id, IModel<List<ResourceNode>> childrenModel) {
		return new AjaxLink<String>(id){
			@Override
			public void onClick(final AjaxRequestTarget target) {
				toggle();
			}
		}.add(visibleIf(not(or(isTrue(expanded),isEmpty(childrenModel)))));
	}

    private Component createCollapseLink(final String id) {
        return new AjaxLink<String>(id){
            @Override
            public void onClick(final AjaxRequestTarget target) {
                toggle();
            }
        }.add(visibleIf(isTrue(expanded)));
    }

    private Component createSelectLink(final String id, final IModel<ResourceNode> model) {
        return new CrossLink(id, new DerivedDetachableModel<String, ResourceNode>(model) {
            @Override
            protected String derive(ResourceNode node) {
                return resourceLinkProvider.getUrlToResource(node, VisualizationMode.DETAILS, DisplayMode.VIEW);
            }
        });
    }

    // ----------------------------------------------------

	private void addQuickInfo(final String string, final IModel<ResourceNode> model, final WebMarkupContainer container) {
		IModel<RBEntity> rbEntity = new LoadableDetachableModel<RBEntity>() {
			@Override
			protected RBEntity load() {
				return entityManager.find(model.getObject());
			}
		};

		Panel infoPanel = new QuickInfoPanel("infoPanel", new ListModel<RBField>(rbEntity.getObject().getQuickInfo()));
		container.add(infoPanel);
	}

	// ------------------------------------------------------

    private void toggle() {
        if(isExpanded()){
            expanded.setObject(Boolean.FALSE);
        } else{
            expanded.setObject(Boolean.TRUE);
        }
        send(this, Broadcast.BUBBLE, EVENT_TREE_UPDATE);
    }

    private ListView<ResourceNode> createChildrenList(final String id, IModel<List<ResourceNode>> model) {
        ListView<ResourceNode> view = new ListView<ResourceNode>(id, model) {
            @Override
            protected void populateItem(final ListItem<ResourceNode> item) {
                item.add(new TreeNodeItemPanel("item", item.getModel(), level+1));
            }
        };
        view.setReuseItems(true);
        view.add(visibleIf(isTrue(expanded)));
        return view;
    }

	private IModel<List<ResourceNode>> getChildren(final IModel<ResourceNode> parent) {
		return new DerivedDetachableModel<List<ResourceNode>, ResourceNode>(parent) {
			@Override
			protected List<ResourceNode> derive(final ResourceNode parent) {
                return TreeStructure.children(parent);
			}
		};
	}

    private boolean isExpanded() {
        return Boolean.TRUE.equals(expanded.getObject());
    }

}
