/*
 * Copyright 2013 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.periphery;

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
public class PeripheryVisualizationPanel extends InfoVisPanel {

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public PeripheryVisualizationPanel(final String id, final IModel<ResourceNode> model) {
		super(id, model, VisualizationMode.PERIPHERY);
	}

	// ----------------------------------------------------

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.JIT_JS));
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.PERIPHERY_JS));
		response.render(OnLoadHeaderItem.forScript("initGraph()"));
	}

	// ----------------------------------------------------

	@Override
	protected InfoVisPath adapt(final InfoVisPath path) {
		return path.tree().withRoot(getModelObject());
	}

}
