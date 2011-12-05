/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;

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
	
	private Object value;
	
	// ----------------------------------------------------
	
	/**
	 * @param predicate
	 */
	public EntityAttributeApplyAction(final ResourceID predicate) {
		this.predicate = predicate;
	}

	// ----------------------------------------------------
	
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void execute(RBEntity target) {
		final RBField field = target.getField(predicate);
		if (field.getValues().isEmpty()) {
			field.setValue(0, value);
		} else {
			field.addValue(value);
		}
		
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public String toString() {
		return "Action(" + predicate + "=" + value + ")";
	}

}
