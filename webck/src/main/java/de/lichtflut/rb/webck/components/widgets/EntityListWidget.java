/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.components.listview.ResourceListPanel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

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
public class EntityListWidget extends AbstractWidget {

	@SpringBean
	protected ServiceProvider provider;

	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	public EntityListWidget(String id, IModel<WidgetSpec> spec) {
		super(id, spec);
		
		final ColumnConfiguration config = ColumnConfiguration.defaultConfig();
		addResolved(config, RB.HAS_FIRST_NAME);
		addResolved(config, RB.HAS_LAST_NAME);
		addResolved(config, RB.HAS_EMAIL);
		addResolved(config, RB.IS_EMPLOYED_BY);
		
		add(new ResourceListPanel("listView", modelFor(spec, 10), config));
	}
	
	// ----------------------------------------------------
	
	protected void addResolved(ColumnConfiguration config, ResourceID predicate) {
		config.addColumnByPredicate(
				provider.getArastejuGate().startConversation().resolve(predicate));
	}

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

}
