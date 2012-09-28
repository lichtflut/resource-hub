/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common.impl;

import de.lichtflut.rb.core.common.SerialNumberOrderedNodesContainer;
import de.lichtflut.rb.webck.common.OrderedNodesContainer;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.List;

/**
 * <p>
 *  Implementation of {@link OrderedNodesContainer} for resource nodes ordered by a serial number.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class OrderedModelContainer extends SerialNumberOrderedNodesContainer implements OrderedNodesContainer{

	private final IModel<List<? extends ResourceNode>> model;
	
	// ----------------------------------------------------
	
	/**
	 * @param model
	 */
	public OrderedModelContainer(IModel<List<? extends ResourceNode>> model) {
		this.model = model;
	}
	
	// ----------------------------------------------------

    @SuppressWarnings("unchecked")
    protected List<ResourceNode> getList() {
        return (List<ResourceNode>) model.getObject();
    }

}
