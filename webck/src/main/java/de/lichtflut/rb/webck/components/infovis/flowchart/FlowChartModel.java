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
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Feb 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class FlowChartModel {
	
	private final SemanticGraph graph;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param graph The data graph.
	 */
	public FlowChartModel(final SemanticGraph graph) {
		this.graph = graph;
	}

	// ----------------------------------------------------

	public Collection<FlowChartNode> getNodes() {
		final Map<QualifiedName, FlowChartNode> nodeMap = new HashMap<QualifiedName, FlowChartNode>();
		final Collection<FlowChartNode> result = new ArrayList<FlowChartNode>();
		for(ResourceNode resource : getResources()) {
			final FlowChartNode flowChartNode = new FlowChartNode(resource);
			nodeMap.put(resource.getQualifiedName(), flowChartNode);
			result.add(flowChartNode);
		}
		
		for (FlowChartNode node : result) {
			node.linkYourself(nodeMap);
		}
		
		return result;
	}
	
	// ----------------------------------------------------

	protected List<ResourceNode> getResources() {
		final List<ResourceNode> resources = new ArrayList<ResourceNode>();
		for (SemanticNode current : graph.getNodes()) {
			if (current.isResourceNode()) {
				resources.add(current.asResource());
			}
		}
		return resources;
	}

}
