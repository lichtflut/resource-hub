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
package de.lichtflut.rb.webck.components.widgets;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.entity.EntityInfoPanel;
import de.lichtflut.rb.webck.components.entity.EntityPanel;
import de.lichtflut.rb.webck.components.widgets.config.EntityDetailsWidgetConfigPanel;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.perceptions.WidgetSelectionModel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.query.QueryResult;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isNull;

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
	private EntityManager entityManager;

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
	}
	
	// ----------------------------------------------------

	protected IModel<RBEntity> modelFor(final IModel<WidgetSpec> spec) {
		return new DerivedDetachableModel<RBEntity, QueryResult>(new WidgetSelectionModel(spec)) {

			@Override
			public RBEntity derive(QueryResult result) {
                if (!result.isEmpty()) {
				    return loadFirst(result);
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
