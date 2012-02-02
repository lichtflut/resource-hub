/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.structure.OrderBySerialNumber;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.editor.VisualizationMode;
import de.lichtflut.rb.webck.components.links.CrossLink;
import de.lichtflut.rb.webck.components.links.LabeledLink;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.ListAction;
import de.lichtflut.rb.webck.components.listview.ResourceListPanel;
import de.lichtflut.rb.webck.components.widgets.config.EntityListWidgetConfigPanel;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

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

	public static final int MAX_RESULTS = 10;
	
	@SpringBean
	protected ServiceProvider provider;
	
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
		
		IModel<List<ResourceNode>> content = modelFor(spec, MAX_RESULTS);
		IModel<ColumnConfiguration> config = configModel(spec);
		
		getDisplayPane().add(new ResourceListPanel("listView", content, config) {
			protected Component createViewAction(String componentId, ResourceNode entity) {
				final CharSequence url = resourceLinkProvider.getUrlToResource(entity, VisualizationMode.DETAILS, DisplayMode.VIEW);
				final CrossLink link = new CrossLink(LabeledLink.LINK_ID, url.toString());
				return new LabeledLink(componentId, link, new ResourceModel("action.view"))
					.setLinkCssClass("action-view")
					.setLinkTitle(new ResourceModel("action.view"));
			};
		});
		
		getDisplayPane().add(new WidgetActionsPanel("actions", spec));
		
	}
	
	// ----------------------------------------------------
	
	protected WebMarkupContainer createConfigurationPane(String componentID, IModel<WidgetSpec> spec) {
		return new EntityListWidgetConfigPanel(componentID, spec);
	};
	
	// ----------------------------------------------------
	
	protected IModel<List<ResourceNode>> modelFor(final IModel<WidgetSpec> spec, final int maxResults) {
		return new AbstractLoadableDetachableModel<List<ResourceNode>>() {
			@Override
			public List<ResourceNode> load() {
				final Query query = provider.getArastejuGate().createQueryManager().buildQuery();
				spec.getObject().getSelection().adapt(query);
				return query.getResult().toList(maxResults);
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
		return provider.getResourceResolver().resolve(node.asResource());
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
