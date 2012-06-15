/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

import org.arastreju.sge.ModelingConversation;
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
public class ResourceAttributeApplyAction implements ReferenceReceiveAction<ResourceID> {

	private final ResourceNode subject;
	
	private final ResourceID predicate;
	
	// ----------------------------------------------------
	
	/**
	 * @param predicate
	 */
	public ResourceAttributeApplyAction(final ResourceNode subject, final ResourceID predicate) {
		this.subject = subject;
		this.predicate = predicate;
	}

	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void execute(final ModelingConversation conversation, final RBEntity createdEntity) {
		conversation.attach(subject);
		SNOPS.assure(subject, predicate, createdEntity.getID());
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public String toString() {
		return "Action(" + subject + "-->" + predicate + ")";
	}

}
