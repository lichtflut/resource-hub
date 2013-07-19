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
package de.lichtflut.rb.webck.components.widgets.list;

import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.components.links.LabeledLink;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.ListAction;
import de.lichtflut.rb.webck.components.listview.ListPagerPanel;
import de.lichtflut.rb.webck.components.listview.ResourceListPanel;
import de.lichtflut.rb.webck.components.navigation.ExtendedActionsPanel;
import de.lichtflut.rb.webck.components.widgets.ConfigurableWidget;
import de.lichtflut.rb.webck.components.widgets.WidgetActionsPanel;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.perceptions.WidgetSelectionModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.basic.PageableModel;
import de.lichtflut.rb.webck.models.resources.ResourceQueryResultModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.QueryResult;

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
public class EntityListWidget extends ConfigurableWidget {

	public static final int MAX_RESULTS = 15;

    @SpringBean
	private SemanticNetworkService semanticNetwork;

	@SpringBean
	private ResourceLinkProvider resourceLinkProvider;

    // ----------------------------------------------------
	
	/**
	 * The constructor.
	 * @param id The component ID.
	 * @param spec The widget specification.
	 * @param isConfigMode Conditional whether the perspective is in configuration mode.
	 */
	public EntityListWidget(String id, IModel<WidgetSpec> spec, ConditionalModel<Boolean> isConfigMode) {
		super(id, spec, isConfigMode);
		
		setOutputMarkupId(true);
		
		final IModel<QueryResult> queryModel = modelFor(spec);
		final IModel<ColumnConfiguration> config = configModel(spec);
		
		getDisplayPane().add(new ExtendedActionsPanel("extendedActionsPanel", queryModel, config)
				.add(ConditionalBehavior.visibleIf(not(isConfigMode))));
		
		final PageableModel<ResourceNode> content = 
				new ResourceQueryResultModel(queryModel, new Model<Integer>(MAX_RESULTS), new Model<Integer>(0));
		
		getDisplayPane().add(new ResourceListPanel("listView", content, config) {
			protected Component createViewAction(String componentId, ResourceNode entity) {
				final CharSequence url = resourceLinkProvider.getUrlToResource(entity, VisualizationMode.DETAILS, DisplayMode.VIEW);
				final CrossLink link = new CrossLink(LabeledLink.LINK_ID, url.toString());
				return new LabeledLink(componentId, link, new ResourceModel("action.view"))
					.setLinkCssClass("action-view")
					.setLinkTitle(new ResourceModel("action.view"));
			}
		});
		
		getDisplayPane().add(new ListPagerPanel("pager", content.getResultSize(), content.getOffset(), content.getPageSize()) {
			public void onPage() {
				RBAjaxTarget.add(EntityListWidget.this);
			}
		});
		
		getDisplayPane().add(new WidgetActionsPanel("actions", spec));
		
	}
	
	// ----------------------------------------------------
	
	protected WebMarkupContainer createConfigurationPane(String componentID, IModel<WidgetSpec> spec) {
		return new EntityListWidgetConfigPanel(componentID, spec);
	}
	
	// ----------------------------------------------------
	
	protected IModel<QueryResult> modelFor(final IModel<WidgetSpec> spec) {
        return new WidgetSelectionModel(spec);
	}
	
	protected IModel<ColumnConfiguration> configModel(final IModel<WidgetSpec> specModel) {
		return new DerivedDetachableModel<ColumnConfiguration, WidgetSpec>(specModel) {
			@Override
			protected ColumnConfiguration derive(WidgetSpec spec) {
				final ColumnConfiguration config = new ColumnConfiguration(ListAction.VIEW);
				for (ColumnDef columnDef : spec.getColumns()) {
					final ResourceID predicate = columnDef.getProperty();
                    final String label = columnDef.getHeader();
					if (predicate != null) {
                        ResourceNode property = semanticNetwork.resolve(predicate.asResource());
                        if (label != null) {
                            config.addColumn(property, label);
                        } else {
                            config.addColumn(property);
                        }
					}
				}
				return config;
			}
		};
	}

}
