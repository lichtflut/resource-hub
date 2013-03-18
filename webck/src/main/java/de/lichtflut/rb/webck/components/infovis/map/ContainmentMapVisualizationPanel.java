/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.map;

import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.infovis.InfoVisPanel;
import de.lichtflut.rb.webck.components.infovis.js.InfoVisJavaScriptResources;
import de.lichtflut.rb.webck.config.InfoVisPath;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Panel for display of a containment map.
 * </p>
 *
 * <p>
 * 	Created Mar 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class ContainmentMapVisualizationPanel extends InfoVisPanel {

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public ContainmentMapVisualizationPanel(String id, IModel<ResourceNode> model) {
		super(id, model, VisualizationMode.MAP);
	}
	
	// ----------------------------------------------------
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.D3_JS);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.MAP_JS);
		response.renderOnLoadJavaScript("showMap();");
	}

    @Override
    protected InfoVisPath adapt(InfoVisPath path) {
        return path.tree().withRoot(getModelObject()).ofType("hierarchy");
    }

}
