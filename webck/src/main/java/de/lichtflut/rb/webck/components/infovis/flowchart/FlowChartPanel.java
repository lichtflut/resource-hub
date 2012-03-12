/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import java.util.Collection;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.webck.components.entity.VisualizationMode;
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
	
	private final IModel<Collection<ResourceNode>> nodeSet; 
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public FlowChartPanel(String id, IModel<ResourceNode> base, IModel<Collection<ResourceNode>> nodeSet) {
		super(id, base, VisualizationMode.FLOW_CHART);
		this.nodeSet = nodeSet;
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
		response.renderOnLoadJavaScript(
				"var paper = Raphael('infovis', 2000, 1000);" +
				"LFRB.FlowChart.drawChart(DataSet.nodeset, DataSet.lanes, paper);"
		);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected IResourceStream getJsonResource() {
		final FlowChartModel model = new FlowChartModel(modeler).add(nodeSet.getObject());
		return new FlowChartJsonStream(model, getLocale());
	}

	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		nodeSet.detach();
	}
}
