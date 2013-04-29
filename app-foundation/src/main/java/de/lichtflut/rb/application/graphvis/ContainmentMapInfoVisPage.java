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

import de.lichtflut.rb.webck.components.infovis.map.ContainmentMapVisualizationPanel;
import de.lichtflut.rb.webck.models.resources.ResourceLoadModel;
import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Page for a map view of graph data.
 * </p>
 *
 * <p>
 * 	Created Mar 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class ContainmentMapInfoVisPage extends AbstractInfoVisPage {

	public ContainmentMapInfoVisPage(final PageParameters parameters) {
		super(parameters);
	}
	
	// ----------------------------------------------------
	
	@Override
	protected Component createInfoVisPanel(String componentID, ResourceID resource) {
		return new ContainmentMapVisualizationPanel(componentID, new ResourceLoadModel(resource));
	}

}
