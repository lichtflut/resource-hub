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

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.Selection;
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
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.basic.PageableModel;
import de.lichtflut.rb.webck.models.resources.ResourceQueryResultModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryException;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.query.SimpleQueryResult;
import org.arastreju.sge.query.SortCriteria;

import java.util.ArrayList;
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
	
	@SpringBean
	private SemanticNetworkService semanticNetwork;

    @SpringBean
    private Conversation conversation;
	
	@SpringBean
	private ResourceLinkProvider resourceLinkProvider;
	
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
		
		final IModel<QueryResult> queryModel = modelFor(spec);





		getDisplayPane().add(new ExtendedActionsPanel("extendedActionsPanel", queryModel, null)
				.add(ConditionalBehavior.visibleIf(not(isConfigMode))));
		getDisplayPane().add(new WidgetActionsPanel("actions", spec));
	}
	
	// ----------------------------------------------------
	
	protected WebMarkupContainer createConfigurationPane(String componentID, IModel<WidgetSpec> spec) {
		return new EntityTreeWidgetConfigPanel(componentID, spec);
	}
	
	// ----------------------------------------------------
	
	protected IModel<QueryResult> modelFor(final IModel<WidgetSpec> spec) {
		return new AbstractLoadableDetachableModel<QueryResult>() {
			@Override
			public QueryResult load() {
				final Selection selection = spec.getObject().getSelection();
				if (selection != null && selection.isDefined()) {
					final Query query = conversation.createQuery();
					query.beginAnd().addField(RDF.TYPE, RBSystem.ENTITY);
					selection.adapt(query);
					query.end();
					try {
						query.setSortCriteria(new SortCriteria(getSortCriteria(spec.getObject())));
						return query.getResult();
					} catch(QueryException e) {
						error("Error while executing query: " + e);
						return SimpleQueryResult.EMPTY;
					}
				} else {
					return SimpleQueryResult.EMPTY;
				}
			}
		};
	}

	private String[] getSortCriteria(WidgetSpec spec) {
		List<String> columns = new ArrayList<String>();
		for (ColumnDef def : spec.getColumns()) {
			final ResourceID predicate = def.getProperty();
			if (predicate != null) {
				columns.add(predicate.toURI());
			}
		}
		return columns.toArray(new String[columns.size()]);
	}

}
