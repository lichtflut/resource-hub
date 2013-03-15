/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.hierarchy;

import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.infovis.InfoVisPanel;
import de.lichtflut.rb.webck.components.infovis.js.InfoVisJavaScriptResources;
import de.lichtflut.rb.webck.config.InfoVisPath;
import org.apache.wicket.markup.html.IHeaderResponse;
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
public class HierarchyVisualizationPanel extends InfoVisPanel {
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public HierarchyVisualizationPanel(String id, IModel<ResourceNode> model) {
		super(id, model, VisualizationMode.HIERARCHY);
	}
	
	// ----------------------------------------------------
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.RAPHAEL_JS);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.HIERARCHY_JS);
		response.renderOnLoadJavaScript(
				"var paper = Raphael('infovis', 2000, 1000);" +
				"showTree(paper);"
		);
	}

    @Override
    protected InfoVisPath adapt(InfoVisPath path) {
        return path.tree().withRoot(getModelObject()).ofType("hierarchy");
    }

}
