/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.arastreju.sge.model.SemanticGraph;

import de.lichtflut.rb.webck.components.infovis.InfoVisPanel;
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
public class FlowChartPanel extends InfoVisPanel {
	
	private final FlowChartModeler modeler; 
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public FlowChartPanel(String id, IModel<SemanticGraph> model) {
		super(id, model);
		this.modeler = new DefaultFlowChartModeler();
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.RAPHAEL_JS);
		response.renderJavaScriptReference(InfoVisJavaScriptResources.FLOWCHART_JS);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected IResourceStream getJsonResource() {
		final FlowChartModel model = new FlowChartModel(modeler).add(getModelObject());
		return new FlowChartJsonStream(model, getLocale());
	}

}
