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
