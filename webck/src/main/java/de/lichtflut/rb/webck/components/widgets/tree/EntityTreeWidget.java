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

import de.lichtflut.rb.core.query.QueryContext;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.navigation.ExtendedActionsPanel;
import de.lichtflut.rb.webck.components.widgets.ConfigurableWidget;
import de.lichtflut.rb.webck.components.widgets.WidgetActionsPanel;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.perceptions.WidgetSelectionModel;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.QueryResult;

import java.util.List;

import static de.lichtflut.rb.webck.models.ConditionalModel.not;

/**
 * <p>
 *  Widget for display of a list of entities.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityTreeWidget extends ConfigurableWidget {

	public static final int MAX_RESULTS = 15;
	
    // ----------------------------------------------------
	
	/**
	 * The constructor.
	 * @param id The component ID.
	 * @param spec The widget specification.
	 * @param isConfigMode Conditional whether the perspective is in configuration mode.
	 */
	public EntityTreeWidget(String id, IModel<WidgetSpec> spec, ConditionalModel<Boolean> isConfigMode) {
		super(id, spec, isConfigMode);
		
		setOutputMarkupId(true);
		
		final IModel<QueryResult> rootModel = new WidgetSelectionModel(spec);
        final ListView<ResourceNode> list = new ListView<ResourceNode>("tree", listModel(rootModel)) {
            @Override
            protected void populateItem(ListItem<ResourceNode> listItem) {
                listItem.add(new TreeNodeItemPanel("root", listItem.getModel()));
            }
        };
        list.setReuseItems(true);

        getDisplayPane().add(list);

        getDisplayPane().add(new ExtendedActionsPanel("extendedActionsPanel", rootModel, null)
                .add(ConditionalBehavior.visibleIf(not(isConfigMode))));
		getDisplayPane().add(new WidgetActionsPanel("actions", spec));
	}
	
	// ----------------------------------------------------
	
	protected WebMarkupContainer createConfigurationPane(String componentID, IModel<WidgetSpec> spec) {
		return new EntityTreeWidgetConfigPanel(componentID, spec);
	}
	
	// ----------------------------------------------------

    @Override
    public void onEvent(IEvent<?> event) {
        Object payload = event.getPayload();
        if (TreeNodeItemPanel.EVENT_TREE_UPDATE.equals(payload)) {
            RBAjaxTarget.add(this);
        }
    }

    protected IModel<List<ResourceNode>> listModel(IModel<QueryResult> queryResult) {
        return new DerivedDetachableModel<List<ResourceNode>, QueryResult>(queryResult) {
            @Override
            protected List<ResourceNode> derive(QueryResult qr) {
                return qr.toList(MAX_RESULTS);
            }
        };
    }

}
