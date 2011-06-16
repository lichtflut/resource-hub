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
 * @param <T> -
 */
public abstract class RBValidator<T extends Object> {

	/**
	 * @param value - the value which shoould be validated
	 * @return [true if the given value is valid and false if not] Please note,
	 * that this is not a part of this this version. An Exception will be raised if invalid.
	 * @throws RBInvalidValueException if the value is generally invalid
	 */
	public abstract boolean isValid(final T value) throws RBInvalidValueException;
}
