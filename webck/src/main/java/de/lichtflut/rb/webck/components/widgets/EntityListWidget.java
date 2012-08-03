/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;
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
import de.lichtflut.rb.webck.components.widgets.config.EntityListWidgetConfigPanel;
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
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryException;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.query.SimpleQueryResult;
import org.arastreju.sge.query.SortCriteria;
import org.arastreju.sge.structure.OrderBySerialNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
	private ModelingConversation conversation;
	
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
	};
	
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
	
	protected IModel<ColumnConfiguration> configModel(final IModel<WidgetSpec> specModel) {
		return new DerivedDetachableModel<ColumnConfiguration, WidgetSpec>(specModel) {
			@Override
			protected ColumnConfiguration derive(WidgetSpec spec) {
				final ColumnConfiguration config = new ColumnConfiguration(ListAction.VIEW);
				for (ResourceNode node : getColumnDefs(spec)) {
					final SemanticNode predicate = SNOPS.fetchObject(node.asResource(), WDGT.CORRESPONDS_TO_PROPERTY);
					if (predicate != null) {
						config.addColumnByPredicate(resolve(predicate));
					}
				}
				return config;
			}
		};
	}
	
	private ResourceNode resolve(SemanticNode node) {
		return conversation.resolve(node.asResource());
	}
	
	private String[] getSortCriteria(WidgetSpec spec) {
		List<ResourceNode> defs = getColumnDefs(spec);
		List<String> columns = new ArrayList<String>();
		for (ResourceNode def : defs) {
			final SemanticNode predicate = SNOPS.fetchObject(def.asResource(), WDGT.CORRESPONDS_TO_PROPERTY);
			if (predicate != null && predicate.isResourceNode()) {
				columns.add(predicate.asResource().toURI());
			}
		}
		return columns.toArray(new String[columns.size()]);
	}
	
	private List<ResourceNode> getColumnDefs(WidgetSpec spec) {
		final Set<SemanticNode> columnDefs = SNOPS.objects(spec, WDGT.DEFINES_COLUMN);
		final List<ResourceNode> result = new ArrayList<ResourceNode>(columnDefs.size());
		for (SemanticNode node : columnDefs) {
			result.add(node.asResource());
		}
		Collections.sort(result, new OrderBySerialNumber());
		return result;
	}

}
