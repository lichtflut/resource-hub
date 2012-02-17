/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class RBException extends Exception {

	private long errorCode;

	// ---------------- Constructor -------------------------

	public RBException(final String msg){
		super(msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public RBException(long errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public RBException(long errorCode, String msg, Throwable e){
		super(msg, e);
		this.errorCode = errorCode;
	}

	// ------------------------------------------------------

	/**
	 * @return the errorCode
	 */
	public long getErrorCode() {
		return errorCode;
	}
	
	
}
