/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

/**
 * <p>
 * Abstract validator.
 * Validator should mainly decide if a value is valid or not
 * </p>
 * 
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 */
public abstract class RBValidator<T extends Object> {
	
	public abstract boolean isValid(T value) throws RBInvalidValueException;	
}
