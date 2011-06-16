/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

/**
 * <p>
 * 	Exception which is fired when something went wrong with an given attribute.
 * </p>
 *
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class RBInvalidAttributeException extends Exception {

	/**
	 * Constructor.
	 * @param message -
	 */
	public RBInvalidAttributeException(final String message){
		super(message);
	}

}
