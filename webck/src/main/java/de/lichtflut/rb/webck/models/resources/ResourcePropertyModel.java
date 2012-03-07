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
public class ResourcePropertyModel<T extends SemanticNode> implements IModel<T> {
	
	private final IModel<? extends ResourceNode> subject;
	
	private final ResourceID predicate;

	private final Context[] ctx;

	// ----------------------------------------------------

	/**
	 * @param subject The subject.
	 * @param predicate The predicate.
	 * @param ctx Optional context.
	 */
	public ResourcePropertyModel(IModel<? extends ResourceNode> subject, ResourceID predicate, Context... ctx) {
		this.subject = subject;
		this.predicate = predicate;
		this.ctx = ctx;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() {
		return (T) SNOPS.fetchObject(subject.getObject(), predicate);
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void setObject(SemanticNode object) {
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
