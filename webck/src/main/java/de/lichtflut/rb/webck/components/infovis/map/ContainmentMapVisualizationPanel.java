/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.map;

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
	public ContainmentMapVisualizationPanel(final String id, final IModel<ResourceNode> model) {
		super(id, model, VisualizationMode.MAP);
	}

	// ----------------------------------------------------

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.D3_JS));
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.MAP_JS));
		response.render(OnLoadHeaderItem.forScript("showMap();"));
	}

	@Override
	protected InfoVisPath adapt(final InfoVisPath path) {
		return path.tree().withRoot(getModelObject()).ofType("hierarchy");
	}

}
