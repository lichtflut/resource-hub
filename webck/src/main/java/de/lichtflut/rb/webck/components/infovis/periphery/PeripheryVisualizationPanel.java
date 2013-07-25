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
package de.lichtflut.rb.webck.components.infovis.periphery;

import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.infovis.InfoVisPanel;
import de.lichtflut.rb.webck.components.infovis.js.InfoVisJavaScriptResources;
import de.lichtflut.rb.webck.config.InfoVisPath;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Panel for display of a flow chart.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PeripheryVisualizationPanel extends InfoVisPanel {

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public PeripheryVisualizationPanel(String id, IModel<ResourceNode> model) {
		super(id, model, VisualizationMode.PERIPHERY);
	}
	
	// ----------------------------------------------------
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.JIT_JS));
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.PERIPHERY_JS));
		response.render(OnLoadHeaderItem.forScript("initGraph()"));
	}

    // ----------------------------------------------------

    @Override
    protected InfoVisPath adapt(InfoVisPath path) {
        return path.tree().withRoot(getModelObject());
    }

}
