/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNull;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryException;
import org.arastreju.sge.query.QueryResult;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.entity.EntityInfoPanel;
import de.lichtflut.rb.webck.components.entity.EntityPanel;
import de.lichtflut.rb.webck.components.widgets.config.EntityDetailsWidgetConfigPanel;
import de.lichtflut.rb.webck.models.ConditionalModel;
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
	protected EntityManager entityManager;
	
	@SpringBean
	protected ModelingConversation conversation;

	// ----------------------------------------------------
	
	/**
	 * The constructor.
	 * @param id The component ID.
	 * @param spec The widget specification.
	 * @param isConfigMode Conditional whether the perspective is in configuration mode.
	 */
	public EntityDetailsWidget(String id, IModel<WidgetSpec> spec, ConditionalModel<Boolean> isConfigMode) {
		super(id, spec, isConfigMode);
		
		final IModel<RBEntity> entityModel = modelFor(spec);
		
		getDisplayPane().add(new EntityInfoPanel("info", entityModel));
		
		getDisplayPane().add(new EntityPanel("entity", entityModel));
		
		getDisplayPane().add(new Label("noEntityHint", new ResourceModel("message.entity-not-found"))
			.add(visibleIf(isNull(entityModel))));
		
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
				final Selection selection = spec.getObject().getSelection();
				if (selection != null && selection.isDefined()) {
					final Query query = conversation.createQuery();
					query.beginAnd().addField(RDF.TYPE, RBSystem.ENTITY);
					selection.adapt(query);
					query.end();
					try {
						final QueryResult result = query.getResult();
						if (!result.isEmpty()) {
							return loadFirst(result);
						}
					} catch(QueryException e) {
						error("Error while executing query: " + e);
					}
				}
				return null;
			}

			protected RBEntity loadFirst(final QueryResult result) {
				final ResourceID id = result.iterator().next();
				result.close();
				if (id == null) {
					return null;
				} else {
					return entityManager.find(id);
				}
			}
		};
	}
	
}
