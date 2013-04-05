/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.hierarchy;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.infovis.InfoVisPanel;
import de.lichtflut.rb.webck.components.infovis.js.InfoVisJavaScriptResources;
import de.lichtflut.rb.webck.config.InfoVisPath;

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
	public HierarchyVisualizationPanel(final String id, final IModel<ResourceNode> model) {
		super(id, model, VisualizationMode.HIERARCHY);
	}

	// ----------------------------------------------------

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.RAPHAEL_JS));
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.HIERARCHY_JS));
		response.render(OnLoadHeaderItem.forScript(
				"var paper = Raphael('infovis', 2000, 1000);" +
						"showTree(paper);"
				));
	}

	@Override
	protected InfoVisPath adapt(final InfoVisPath path) {
		return path.tree().withRoot(getModelObject()).ofType("hierarchy");
	}

}
