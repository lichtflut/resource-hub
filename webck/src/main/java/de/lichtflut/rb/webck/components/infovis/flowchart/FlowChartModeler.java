/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import org.arastreju.sge.model.nodes.ResourceNode;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 *  Base interface for a modeler of o flow chart.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface FlowChartModeler extends Serializable {

	boolean isFlowChartNode(ResourceNode node);
	
	ResourceNode getLane(ResourceNode node);
	
	Collection<ResourceNode> getPredecessors(ResourceNode node);

}
