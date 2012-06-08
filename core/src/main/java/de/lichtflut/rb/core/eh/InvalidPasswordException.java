/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Exception thrown when a given password was invalid.
 * </p>
 *
 * <p>
 * 	Created May 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class InvalidPasswordException extends RBAuthException {

	public InvalidPasswordException(String msg) {
		super(ErrorCodes.INVALID_PASSWORD, msg);
	}

}
