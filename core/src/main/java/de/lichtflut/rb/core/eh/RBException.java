/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Basic exception for Resource Browser.
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class RBException extends Exception {

	private int errorCode;

	// ---------------- Constructor -------------------------

	public RBException(final String msg){
		super(msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public RBException(int errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public RBException(int errorCode, String msg, Throwable e){
		super(msg, e);
		this.errorCode = errorCode;
	}

	// ------------------------------------------------------

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	
}
