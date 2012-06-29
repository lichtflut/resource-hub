/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import org.arastreju.sge.model.nodes.ResourceNode;

import java.io.Serializable;

/**
 * <p>
 *  Interface for ordering of a group of nodes.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface OrderedNodesContainer extends Serializable {
	
	void moveUp(ResourceNode node, int positions);
	
	void moveDown(ResourceNode node, int positions);
	
	void swap(ResourceNode a, ResourceNode b);

}
