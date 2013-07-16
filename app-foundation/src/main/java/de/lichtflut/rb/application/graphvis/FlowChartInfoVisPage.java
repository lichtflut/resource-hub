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
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.webck.components.infovis.flowchart.FlowChartPanel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.resources.ResourceLoadModel;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.structure.TreeStructure;

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
	 * @param parameters The page paramters.
	 */
	public FlowChartInfoVisPage(PageParameters parameters) {
		super(parameters);
	}
	
	// ----------------------------------------------------
	
	@Override
	protected Component createInfoVisPanel(String componentID, ResourceID resource) {
		final ResourceLoadModel baseModel = new ResourceLoadModel(resource);
		return new FlowChartPanel(componentID, baseModel, new ChartModel(baseModel));
	}
	
	// ----------------------------------------------------
	
	private final class ChartModel extends DerivedDetachableModel<Collection<ResourceNode>, ResourceNode> {

		private ChartModel(IModel<ResourceNode> base) {
			super(base);
		}

		@Override
		protected Collection<ResourceNode> derive(ResourceNode base) {
			final List<ResourceNode> chartNodes = TreeStructure.children(base);
			if (chartNodes.isEmpty()) {
				// add the node itself instead of it's children
				chartNodes.add(base);
			}
			return chartNodes;
		}

	}
	
	
	
}
