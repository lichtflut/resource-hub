/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.editor.EntityPanel;
import de.lichtflut.rb.webck.components.widgets.config.EntityDetailsWidgetConfigPanel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Widget for display of an entity's details.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityDetailsWidget extends ConfigurableWidget {
	
	@SpringBean
	protected ServiceProvider provider;

	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	public EntityDetailsWidget(String id, IModel<WidgetSpec> spec) {
		super(id, spec);
		
		getDisplayPane().add(new EntityPanel("entity", modelFor(spec)));
		
	}
	
	// ----------------------------------------------------
	
	protected WebMarkupContainer createConfigurationPane(String componentID, IModel<WidgetSpec> spec) {
		return new EntityDetailsWidgetConfigPanel(componentID, spec);
	};
	
	// ----------------------------------------------------
	
	protected IModel<RBEntity> modelFor(final IModel<WidgetSpec> spec) {
		return new AbstractLoadableDetachableModel<RBEntity>() {
			@Override
			public RBEntity load() {
				final Query query = provider.getArastejuGate().createQueryManager().buildQuery();
				spec.getObject().getSelection().adapt(query);
				final QueryResult result = query.getResult();
				if (result.isEmpty()) {
					return null;
				} else {
					final ResourceID id = result.iterator().next();
					result.close();
					if (id == null) {
						return null;
					} else {
						return provider.getEntityManager().find(id);
					}
				}
				
			}
		};
	}
	
}
