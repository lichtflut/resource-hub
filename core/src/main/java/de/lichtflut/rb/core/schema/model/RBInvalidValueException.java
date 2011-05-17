/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;


/**
 * <p>
 * 	Exception which is fired when something went wrong with a given value
 * </p>
 * 
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class RBInvalidValueException extends Exception {
	
	public RBInvalidValueException(String message){
		super(message);
	}

}
