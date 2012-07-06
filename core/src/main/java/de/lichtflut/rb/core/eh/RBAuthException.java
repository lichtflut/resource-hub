/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Exception for issues regarding authentication or authorization.
 * </p>
 *
 * <p>
 * 	Created May 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBAuthException extends RBException {

	/**
	 * @param errorCode
	 * @param msg
	 */
	public RBAuthException(int errorCode, String msg) {
		super(errorCode, msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 * @param e
	 */
	public RBAuthException(int errorCode, String msg, Throwable e) {
		super(errorCode, msg, e);
	}

}
