/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.events;

import org.apache.wicket.event.IEvent;

import de.lichtflut.infra.Infra;

/**
 * <p>
 *  Event representing a model change.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ModelChangeEvent<T> {
	
	public static final String NAMESPACE_CREATED = "created.namespace";
	
	public static final String CONTEXT_CREATED = "created.context";
	
	public static final String USER_CREATED = "creted.user";
	
	public static final String TYPE_SELECTED = "selected.type";
	
	public static final String PUBLIC_TYPE_DEFINITION_SELECTED = "selected.public-type-definition";
	
	private static final ModelChangeEvent EMPTY = new ModelChangeEvent(null);
	
	// ----------------------------------------------------
	
	private final T payload;
	
	private final String type;
	
	// ----------------------------------------------------
	
	public static <T> ModelChangeEvent<T> from(IEvent event) {
		Object payload = event.getPayload();
		if (payload instanceof ModelChangeEvent) {
			return (ModelChangeEvent<T>) payload;
		} else {
			return EMPTY;
		}
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param payload
	 * @param type
	 */
	public ModelChangeEvent(final String type, final T payload) {
		this.payload = payload;
		this.type = type;
	}
	
	/**
	 * @param payload
	 * @param type
	 */
	public ModelChangeEvent(final String type) {
		this(type, null);
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the payload
	 */
	public T getPayload() {
		return payload;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	public boolean isAbout(final String type) {
		return Infra.equals(this.type, type);
	}

}
