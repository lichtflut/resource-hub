/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 	Represents an instance of a well defined ResourceType (RT)
 * </p>
 * 
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 */
public interface ResourceTypeInstance<T> extends Serializable{

	enum MetaDataKeys{
		MAX,
		MIN,
		CURRENT,
		TYPE
	}
	
	
	
	/**
	 * @param attribute
	 * @param value
	 * @throws <p>InvalidValueException when the value does not match the required datatype or constraints
	 * or if the maximum count of possible values is reached (depends on the cardinality).
	 * InvalidAttributeException if this attribute does not exists.</p>
	 * @return the index where this value has been stored
	 */
	public int addValueFor(String attribute, T value) throws RBInvalidValueException, RBInvalidAttributeException;
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public T getValueFor(String attribute, int index);
	
	// -----------------------------------------------------
	
	
	/**
	 * 
	 */
	public Collection<T> getValuesFor(String attribute);
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public RBValidator getValidatorFor(String attribute);
	
	// -----------------------------------------------------
	
	
	
	
	
}
