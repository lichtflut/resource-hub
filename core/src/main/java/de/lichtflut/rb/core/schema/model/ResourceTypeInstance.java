/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;

import org.arastreju.sge.model.ResourceID;

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
	 * @return the ticket where this value has been stored
	 */
	public Integer addValueFor(String attribute, T value) throws RBInvalidValueException, RBInvalidAttributeException;
	
	// -----------------------------------------------------
	
	
	public String getSimpleAttributeName(String attribute);
	
	// -----------------------------------------------------
		
	
	/**
	 * 
	 */
	public void addValueFor(String attribute, String value, int ticket) throws RBInvalidValueException, RBInvalidAttributeException;
	
	
	/**
	 * 
	 */
	public T getValueFor(String attribute, int index);
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public Collection<String> getAttributeNames();
	
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
	
	/**
	 * 
	 */
	public Integer generateTicketFor(String attribute) throws RBInvalidValueException, RBInvalidAttributeException;
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public void releaseTicketFor(String attribute, int ticket ) throws RBInvalidValueException,RBInvalidAttributeException;
		
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public Object  geMetaInfoFor(String attribute, MetaDataKeys key);
	
	// -----------------------------------------------------

	/**
	 * 
	 */
	public ResourceSchema getResourceSchema();
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public ResourceID getResourceTypeID();
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public ResourceID getResourceID();
	
	
	// -----------------------------------------------------
	
}
