/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 *  Action setting an entities attribute to a given value.
 * </p>
 *
 * <p>
 * 	Created Dec 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceAttributeApplyAction implements Action<ResourceID> {
	
	private final ResourceID predicate;
	
	private ResourceID value;
	
	// ----------------------------------------------------
	
	/**
	 * @param predicate
	 */
	public ResourceAttributeApplyAction(final ResourceID predicate) {
		this.predicate = predicate;
	}

	// ----------------------------------------------------
	
	/**
	 * @param value the value to set
	 */
	public void setValue(ResourceID value) {
		this.value = value;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void execute(final RBEntity target) {
		final ResourceNode node = target.getNode();
		SNOPS.assure(node, predicate, value);
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public String toString() {
		return "Action(" + predicate + "=" + value + ")";
	}

}
