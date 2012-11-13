/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.eh;

/**
 * <p>
 * Execption for Validation Errors.
 * </p>
 * Created: Nov 2, 2012
 *
 * @author Ravi Knox
 */
public class ValidationException extends RBException {

	/**
	 * {@inheritDoc}
	 */
	public ValidationException(final int errorCode, final String msg, final Throwable e) {
		super(errorCode, msg, e);
	}

	/**
	 * {@inheritDoc}
	 */
	public ValidationException(final int errorCode, final String msg) {
		super(errorCode, msg);
	}

	/**
	 * {@inheritDoc}
	 */
	public ValidationException(final String msg) {
		super(msg);
	}

}
