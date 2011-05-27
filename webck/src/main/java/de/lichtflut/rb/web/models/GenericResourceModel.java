/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.models;

import org.apache.wicket.model.IModel;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;

/**
 * [DESCRIPTION]
 * 
 *  <p>
 * 	Created May 18, 2011
 * </p>
 * 
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class GenericResourceModel<T> implements IModel<T> {
	private ResourceTypeInstance<String> instance;
	private String attribute;
	private Integer ticket;
	private T objectreference;
	
	public GenericResourceModel(ResourceTypeInstance<String> instance, String attribute, int ticket){
		this.ticket = ticket;
		this.instance = instance;
		this.attribute = attribute;
	}
	
	public GenericResourceModel(ResourceTypeInstance<String> instance, String attribute){
		this.instance = instance;
		this.attribute = attribute;
		try {
			if(ticket==null) ticket = instance.generateTicketFor(attribute);
		} catch (Exception e){
			throw new IllegalArgumentException(e);
		}

	}
	
	public T getObject() {
		return convertStringToT(instance.getValueFor(attribute, ticket));
	}


	public void setObject(T object) {
		try {
			instance.addValueFor(attribute,convertObject(object), ticket);
		} catch (Exception e){
			throw new IllegalArgumentException(e);
		}
		
	}

	public void detach() {
		//Do nothing
	}

	
	private String convertObject(T object){
		if(objectreference instanceof Boolean){
			return ((Boolean)object).toString();
		}
		return object.toString();
	}

	@SuppressWarnings("unchecked")
	private T convertStringToT(String value) {
		if(objectreference instanceof Boolean){
				return (T) new Boolean(value.toLowerCase());
		}
		return (T) value;
	}

	
}
