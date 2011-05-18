/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.genericresource;

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
public class GenericResourceModel implements IModel<String> {
	private ResourceTypeInstance<String> instance;
	private String attribute;
	private Integer ticket;
	
	public GenericResourceModel(ResourceTypeInstance<String> instance, String attribute){
		
		this.instance = instance;
		this.attribute = attribute;
		try {
			if(ticket==null) ticket = instance.generateTicketFor(attribute);
		} catch (Exception e){
			throw new IllegalArgumentException(e);
		}
	}
	
	public String getObject() {
		return (String) instance.getValueFor(attribute, ticket);
	}

	public void setObject(String object) {
		try {
			instance.addValueFor(attribute, object, ticket);
		} catch (Exception e){
			throw new IllegalArgumentException(e);
		}
		
	}

	public void detach() {
		//Do nothing
	}
}
