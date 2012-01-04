/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.services.ServiceProvider;

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
public class EntityAttributeApplyAction implements Action<Object> {
	
	private final ResourceID predicate;
	
	private final RBEntity subject;
	
	// ----------------------------------------------------
	
	/**
	 * @param predicate
	 */
	public EntityAttributeApplyAction(final RBEntity subject, final ResourceID predicate) {
		this.subject = subject;
		this.predicate = predicate;
	}

	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void execute(final ServiceProvider sp, final RBEntity createdEntity) {
		final RBField field = subject.getField(predicate);
		if (field.getValues().isEmpty()) {
			field.setValue(0, createdEntity.getID());
		} else {
			field.addValue(createdEntity.getID());
		}
		sp.getEntityManager().store(subject);
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public String toString() {
		return "Action(" + subject + " --> " + predicate + ")";
	}

}
