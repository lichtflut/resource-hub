/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Exception thrown when the user of a request or operation is not authenticated.
 * </p>
 *
 * <p>
 * 	Created Jul 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class UnauthenticatedUserException extends RBAuthException {

	public UnauthenticatedUserException(String msg) {
		super(ErrorCodes.SECURITY_UNAUTHENTICATED_USER, msg);
	}

}
