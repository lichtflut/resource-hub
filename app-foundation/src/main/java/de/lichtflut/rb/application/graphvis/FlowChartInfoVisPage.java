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
package de.lichtflut.rb.application.graphvis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.webck.components.infovis.flowchart.FlowChartPanel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.resources.ResourceLoadModel;
import org.arastreju.sge.model.nodes.views.SNProperty;

/**
 * <p>
 *  Page displaying a flow chart.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class FlowChartInfoVisPage extends AbstractInfoVisPage {
	
	/**
	 * Constructor.
	 * @param parameters
	 */
	public FlowChartInfoVisPage(PageParameters parameters) {
		super(parameters);
	}
	
	// ----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Component createInfoVisPanel(String componentID, ResourceID resource) {
		final ResourceLoadModel baseModel = new ResourceLoadModel(resource);
		return new FlowChartPanel(componentID, baseModel, new ChartModel(baseModel));
	};
	
	// ----------------------------------------------------
	
	private final class ChartModel extends DerivedDetachableModel<Collection<ResourceNode>, ResourceNode> {

		private ChartModel(IModel<ResourceNode> base) {
			super(base);
		}

		@Override
		protected Collection<ResourceNode> derive(ResourceNode base) {
			final Set<ResourceNode> chartNodes = getChildNodes(base);
			if (chartNodes.isEmpty()) {
				// add the node itself instead of it's children
				chartNodes.add(base);
			}
			return chartNodes;
		}

		protected Set<ResourceNode> getChildNodes(final ResourceNode node) {
			final Set<ResourceNode> chartNodes = new HashSet<ResourceNode>();
			for (Statement stmt : node.getAssociations()) {
				if (SNProperty.from(stmt.getPredicate()).isSubPropertyOf(RB.HAS_CHILD_NODE)) {
					chartNodes.add(stmt.getObject().asResource());	
				}
			}
			return chartNodes;
		}
	}
	
	
	
}
