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
 * @param <T>
 */
public abstract class RBValidatorFactory<T extends Object> {

	/**
	 * Checks wheather validation was successfull or not.
	 * @param value -
	 * @return true if validated successfull, false if not
	 * @throws RBInvalidValueException -
	 */
	public abstract boolean isValid(T value) throws RBInvalidValueException;
}
