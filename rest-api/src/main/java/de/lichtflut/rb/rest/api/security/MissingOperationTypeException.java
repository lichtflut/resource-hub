/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.security;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created May 9, 2012
 */
public class MissingOperationTypeException extends RuntimeException {

	/**
	 * @param string
	 */
	public MissingOperationTypeException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
