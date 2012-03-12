/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;

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
	
	private final ResourceNode unassignedLane = new SNResource();
	
	private final Map<ResourceNode, Lane> laneMap = new HashMap<ResourceNode, Lane>();
	
	private final Map<ResourceNode, FlowChartNode> nodeMap = new HashMap<ResourceNode, FlowChartNode>();
	
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

	public FlowChartModel add(Collection<ResourceNode> nodes) {
		for (ResourceNode node : nodes) {
			addFlowChartNode(node.asResource());
		}
		calculatePredecessors();
		return this;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the lanes
	 */
	public List<Lane> getLanes() {
		return new ArrayList<Lane>(laneMap.values());
	}
	
	public List<FlowChartNode> getNodes() {
		return new ArrayList<FlowChartNode>(nodeMap.values());
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param asResource
	 */
	private void addFlowChartNode(ResourceNode node) {
		final FlowChartNode flowChartNode = new FixedDurationFlowChartNode(node, "n" + nodeMap.size());
		nodeMap.put(node, flowChartNode);
		
		ResourceNode laneNode = modeler.getLane(node);
		Lane lane = getOrCreateLane(laneNode);
		flowChartNode.setLane(lane);
	}
	
	private Lane getOrCreateLane(ResourceNode laneNode) {
		if (laneMap.containsKey(laneNode)) {
			return laneMap.get(laneNode); 
		} else if (laneNode != null) {
			final Lane lane = new Lane(laneNode, "l" + laneMap.size());
			laneMap.put(laneNode, lane);
			return lane;
		} else {
			return getUnassignedLane();
		}
	}

	private void calculatePredecessors() {
		for (FlowChartNode node : nodeMap.values()) {
			final Collection<ResourceNode> predecessors = modeler.getPredecessors(node.getResourceNode());
			if (predecessors != null && !predecessors.isEmpty()) {
				addPredecessors(node, predecessors);
			}
		}
	}
	
	private void addPredecessors(FlowChartNode node, Collection<ResourceNode> predecessors) {
		for (ResourceNode predecessor : predecessors) {
			if (nodeMap.containsKey(predecessor)) {
				node.addPredecessor(nodeMap.get(predecessor));
			}
		}
	}
	
	private Lane getUnassignedLane() {
		if (laneMap.containsKey(unassignedLane)) {
			return laneMap.get(unassignedLane);
		} else {
			final Lane lane = new Lane("unnassigned");
			laneMap.put(unassignedLane, lane);
			return lane;
		}
	}
	
}
