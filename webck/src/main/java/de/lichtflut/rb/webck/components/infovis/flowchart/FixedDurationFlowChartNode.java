/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Representation of a node in a flow chart.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class FixedDurationFlowChartNode extends FlowChartNode {
	
	private static float duration = 2.0f;
	
	private static float padding = 0.5f;
	
	// ----------------------------------------------------
	
	/**
	 * @param node
	 * @param id
	 */
	public FixedDurationFlowChartNode(ResourceNode node, String id) {
		super(node,id);
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the start
	 */
	public float getStart() {
		if (getPredecessors().isEmpty()) {
			return 0;
		} else {
			float offset = 0;
			for (FlowChartNode pre : getPredecessors()) {
				offset = Math.max(offset, pre.getEnd() + padding);
			}
			return offset;
		}
	}

	/**
	 * @return the end
	 */
	public float getEnd() {
		return getStart() + duration;
	}

}
