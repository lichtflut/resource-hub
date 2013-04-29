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

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.services.EntityManager;

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
public class EntityAttributeApplyAction implements ReferenceReceiveAction<Object> {

	private final ResourceID predicate;

	private final RBEntity subject;

	@SpringBean
	private EntityManager entityManager;

	// ----------------------------------------------------

	/**
	 * @param predicate
	 */
	public EntityAttributeApplyAction(final RBEntity subject, final ResourceID predicate) {
		this.subject = subject;
		this.predicate = predicate;
		Injector.get().inject(this);
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final Conversation sp, final RBEntity createdEntity) {
		final RBField field = subject.getField(predicate);
		if (field.getValues().isEmpty()) {
			field.setValue(0, createdEntity.getID());
		} else {
			field.addValue(createdEntity.getID());
		}
		entityManager.store(subject);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Action(" + subject + " --> " + predicate + ")";
	}

}
