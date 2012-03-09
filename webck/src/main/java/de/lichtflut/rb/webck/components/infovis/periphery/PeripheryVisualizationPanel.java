/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.periphery;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.webck.components.infovis.InfoVisPanel;
import de.lichtflut.rb.webck.components.infovis.common.JitJsonStream;
import de.lichtflut.rb.webck.components.infovis.js.InfoVisJavaScriptResources;

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
		super(id, model);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.JIT_JS);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.PERIPHERY_JS);
		response.renderOnLoadJavaScript("initGraph()");
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected IResourceStream getJsonResource() {
		return new JitJsonStream(getModelObject());
	}

}
