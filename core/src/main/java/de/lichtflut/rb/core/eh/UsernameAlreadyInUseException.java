/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Exception thrown when a requested username is already in use.
 * </p>
 *
 * <p>
 * 	Created May 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class UsernameAlreadyInUseException extends RBAuthException {

	public UsernameAlreadyInUseException(String msg) {
		super(ErrorCodes.SECURITYSERVICE_USERNAME_ALREADY_IN_USE, msg);
	}

}
