/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Represents a node in a flow chart that is based on a semantic node.
 * </p>
 *
 * <p>
 * 	Created Feb 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class FlowChartNode {
	
	private ResourceNode resource;
	
	private Set<FlowChartNode> successors = new HashSet<FlowChartNode>();
	
	private Set<FlowChartNode> predecessors = new HashSet<FlowChartNode>();

	// ----------------------------------------------------
	
	/**
	 * @param resource
	 */
	public FlowChartNode(ResourceNode resource) {
		this.resource = resource;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the resource
	 */
	public ResourceNode getResource() {
		return resource;
	}
	
	/**
	 * @return the predecessors
	 */
	public Set<FlowChartNode> getPredecessors() {
		return predecessors;
	}
	
	/**
	 * @return the successors
	 */
	public Set<FlowChartNode> getSuccessors() {
		return successors;
	}
	
	// ----------------------------------------------------

	/**
	 * Link this node to it's successors and predecessors.
	 * @param nodeMap A map from qualified names to flow chart nodes.
	 */
	public void linkYourself(Map<QualifiedName, FlowChartNode> nodeMap) {
		for (Statement stmt : resource.getAssociations()) {
			if (isSuccessorPredicate(stmt.getPredicate())) {
				successors.add(nodeMap.get(stmt.getObject().asResource().getQualifiedName()));
			} else if (isPredecessorPredicate(stmt.getPredicate())) {
				successors.add(nodeMap.get(stmt.getObject().asResource().getQualifiedName()));
			}
		};
	}
	
	public void addPredecessor(FlowChartNode node) {
		predecessors.add(node);
	}
	
	public void addSuccessor(FlowChartNode node) {
		successors.add(node);
	}
	
	// ----------------------------------------------------
	
	private boolean isSuccessorPredicate(ResourceID predicate) {
		return false;
	}
	
	private boolean isPredecessorPredicate(ResourceID predicate) {
		return false;
	}

}
