/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.infra.data.MultiMap;

/**
 * <p>
 *  Model of a flow chart.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class FlowChartModel {
	
	private final Map<String, ResourceNode> laneMap = new HashMap<String, ResourceNode>();
	
	private final MultiMap<ResourceNode, ResourceNode> predecessorMap = new MultiMap<ResourceNode, ResourceNode>();
	
	private final FlowChartModeler modeler;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param modeler
	 */
	public FlowChartModel(FlowChartModeler modeler) {
		this.modeler = modeler;
	}
	
	// ----------------------------------------------------

	public FlowChartModel add(SemanticGraph graph) {
		for (SemanticNode node : graph.getNodes()) {
			if (node.isResourceNode() && modeler.isFlowChartNode(node.asResource())) {
				addFlowChartNode(node.asResource());
			}
		}
		return this;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the lanes
	 */
	public List<String> getLanes() {
		return new ArrayList<String>(laneMap.keySet());
	}
	
	public List<ResourceNode> getNodes() {
		return new ArrayList<ResourceNode>(laneMap.values());
	}
	
	public MultiMap<ResourceNode, ResourceNode> getPredecessorMap() {
		return predecessorMap;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param asResource
	 */
	private void addFlowChartNode(ResourceNode node) {
		final String lane = modeler.getLane(node);
		if (!laneMap.containsKey(lane)) {
			laneMap.put(lane, node);
		}
		final Collection<ResourceNode> predecessors = modeler.getPredecessors(node);
		if (predecessors != null && !predecessors.isEmpty()) {
			predecessorMap.addAll(node, predecessors);
		}
	}

}
