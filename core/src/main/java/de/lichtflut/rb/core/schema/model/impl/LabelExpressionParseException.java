/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class LabelExpressionParseException extends Exception {

	/**
	 * @param msg
	 * @param t
	 */
	public LabelExpressionParseException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * @param msg
	 */
	public LabelExpressionParseException(String msg) {
		super(msg);
	}

	
	
}
