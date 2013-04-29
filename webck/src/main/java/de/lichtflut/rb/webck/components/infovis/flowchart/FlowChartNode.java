/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.HashSet;
import java.util.Set;

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
public abstract class FlowChartNode {
	
	private final Set<FlowChartNode> predecessors = new HashSet<FlowChartNode>();
	
	private final ResourceNode node;
	
	private final String id;
	
	private Lane lane;
	
	// ----------------------------------------------------

	/**
	 * @param node
	 * @param id
	 */
	public FlowChartNode(ResourceNode node, String id) {
		this.node = node;
		this.id = id;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the node
	 */
	public ResourceNode getResourceNode() {
		return node;
	}
	
	/**
	 * @return the predecessors
	 */
	public Set<FlowChartNode> getPredecessors() {
		return predecessors;
	}
	
	/**
	 * @param flowChartNode
	 */
	public void addPredecessor(FlowChartNode predecessor) {
		predecessors.add(predecessor);
	}

	/**
	 * @return the lane
	 */
	public Lane getLane() {
		return lane;
	}

	/**
	 * @param lane the lane to set
	 */
	public void setLane(Lane lane) {
		this.lane = lane;
	}

	// ----------------------------------------------------
	
	/**
	 * @return
	 */
	public abstract float getStart();
	
	/**
	 * @return
	 */
	public abstract float getEnd();

}
