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
package de.lichtflut.rb.webck.browsing;

import de.lichtflut.rb.core.entity.RBEntity;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

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
	public void execute(final Conversation conversation, final RBEntity createdEntity) {
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
