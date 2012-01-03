/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.resources;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNText;

/**
 * <p>
 *  Model for getting and setting a ResourceNode's property.
 * </p>
 *
 * <p>
 * 	Created Jan 3, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourcePropertyModel implements IModel<SNText> {
	
	private final IModel<ResourceNode> subject;
	
	private final ResourceID predicate;

	private final Context[] ctx;

	// ----------------------------------------------------

	/**
	 * @param subject The subject.
	 * @param predicate The predicate.
	 * @param ctx Optional context.
	 */
	public ResourcePropertyModel(IModel<ResourceNode> subject, ResourceID predicate, Context... ctx) {
		this.subject = subject;
		this.predicate = predicate;
		this.ctx = ctx;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public SNText getObject() {
		final SemanticNode node = SNOPS.singleObject(subject.getObject(), predicate);
		if (node != null) {
			return node.asValue().asText();
		} else {
			return null;
		}
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void setObject(SNText object) {
		SNOPS.assure(subject.getObject(), predicate, object, ctx);
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void detach() {
		subject.detach();
	}

}
