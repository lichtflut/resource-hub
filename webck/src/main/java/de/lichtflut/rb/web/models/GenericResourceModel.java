/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.models;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.RBInvalidAttributeException;
import de.lichtflut.rb.core.schema.model.RBInvalidValueException;

/**
 * TODO: [DESCRIPTION]
 *
 * <p>
 * ...
 * If the second constructor is used. It might return an {@link IllegalArgumentException} if not ticket
 * could be created.
 * ...
 * the detach-method does not have any affect. this model is not detachable
 * </p>
 *
 *  <p>
 * 	Created May 18, 2011
 * </p>
 *
 * @author Nils Bleisch
 * 
 * @param <T> /
 */
@SuppressWarnings("serial")
public class GenericResourceModel<T> implements IModel<T> {
	private RBEntity<Object> instance;
	private String attribute;
	private Integer ticket;
	private T objectreference;

	// ----------------------------------------
	/**
	 * @param instance /
	 * @param attribute /
	 * @param ticket /
	 */
	public GenericResourceModel(final RBEntity<Object> instance, final String attribute, final int ticket){
		this.ticket = ticket;
		this.instance = instance;
		this.attribute = attribute;
	}

	// ----------------------------------------

	/**
	 * @param instance /
	 * @param attribute /
	 */
	public GenericResourceModel(final RBEntity<Object> instance, final String attribute){
		this.instance = instance;
		this.attribute = attribute;
		try {
			if(ticket==null) {
				ticket = instance.generateTicketFor(attribute);
			}
		} catch (RBInvalidValueException e){
			throw new IllegalArgumentException(e);
		}catch(RBInvalidAttributeException e){
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * @return /
	 */
	public T getObject() {
		T final_value=null;
		Object val = instance.getValueFor(attribute, ticket);
		if(val instanceof String) {
			final_value = convertStringToT((String) val);
		} else {
			final_value = convertObjectToT(val);
		}
		return final_value;
	}

	// ----------------------------------------

	/**
	 * @param object /
	 */
	public void setObject(final T object) {
		try {
			instance.addValueFor(attribute,convertObject(object), ticket);
		} catch (Exception e){
			throw new IllegalArgumentException(e);
		}

	}

	// ----------------------------------------

	/**
	 *
	 */
	public void detach() {
		//Do nothing
	}

	// ----------------------------------------

	/**
	 * @param object /
	 * @return /
	 */
	private String convertObject(final T object){
		if(objectreference instanceof Boolean){
			return ((Boolean)object).toString();
		}
		return object.toString();
	}

	// ----------------------------------------

	/**
	 * @param value /
	 * @return /
	 */
	@SuppressWarnings("unchecked")
	private T convertStringToT(final String value) {
		if(objectreference instanceof Boolean){
				return (T) new Boolean(value.toLowerCase());
		}
		return (T) value;
	}

	/**
	 * @param value /
	 * @return /
	 */
	@SuppressWarnings("unchecked")
	private T convertObjectToT(final Object value) {
		return (T) value;
	}


}
