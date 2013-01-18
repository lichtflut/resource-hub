/*
 * Copyright 2013 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import de.lichtflut.rb.webck.components.infovis.InfoVisPanel;
import de.lichtflut.rb.webck.components.infovis.js.InfoVisJavaScriptResources;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.resource.IResourceStream;
import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.Collection;

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
	 * @param base The model containing the base node.
	 */
	public FlowChartPanel(String id, IModel<ResourceNode> base, IModel<Collection<ResourceNode>> nodeSet) {
		super(id, base, VisualizationMode.FLOW_CHART);
		this.nodeSet = nodeSet;
		this.modeler = new DefaultFlowChartModeler();
	}
	
	// ----------------------------------------------------
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.RAPHAEL_JS));
		response.render(JavaScriptHeaderItem.forReference(InfoVisJavaScriptResources.FLOWCHART_JS));
		response.render(OnLoadHeaderItem.forScript(
                "var paper = Raphael('infovis', 2000, 1000);" +
                        "LFRB.FlowChart.drawChart(DataSet.nodeset, DataSet.lanes, paper);"
        ));
	}
	
	protected IResourceStream getJsonResource() {
		final FlowChartModel model = new FlowChartModel(modeler).add(nodeSet.getObject());
		return new FlowChartJsonStream(model, getLocale());
	}

	// ----------------------------------------------------
	
	@Override
	protected void onDetach() {
		super.onDetach();
		nodeSet.detach();
	}
}
