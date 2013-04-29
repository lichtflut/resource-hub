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
package de.lichtflut.rb.webck.components.infovis;

import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.infovis.common.CurrentNodeInfoPanel;
import de.lichtflut.rb.webck.components.infovis.js.InfoVisJavaScriptResources;
import de.lichtflut.rb.webck.config.DefaultInfoVisServicePathBuilder;
import de.lichtflut.rb.webck.config.InfoVisPath;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Basic information visualization panel.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class InfoVisPanel extends TypedPanel<ResourceNode> {

    @SpringBean
    private ServiceContext context;

    // ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model Model containing the initially selected node.
	 * @param mode The visualization mode.
	 */
	public InfoVisPanel(String id, IModel<ResourceNode> model, VisualizationMode mode) {
		super(id, model);
		
		add(new CurrentNodeInfoPanel("info", model, mode));
	}
	
	// ----------------------------------------------------
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.INFOVIS_JS);

		response.renderJavaScript("LFRB.InfoVis.contextPath='"
                + RequestCycle.get().getRequest().getContextPath() + "';", "LFRB.InfoVis.contextPath");


        final InfoVisPath path = adapt(new DefaultInfoVisServicePathBuilder().create(context.getDomain()));
        response.renderJavaScript("LFRB.InfoVis.serviceURI='" + path.toURI() + "';", "LFRB.InfoVis.serviceURI");
	}

    // ----------------------------------------------------

    protected abstract InfoVisPath adapt(InfoVisPath path);

}
