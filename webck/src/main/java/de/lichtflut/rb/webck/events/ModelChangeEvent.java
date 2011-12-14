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
	
	public static final String NAMESPACE = "namespace";
	
	public static final String CONTEXT = "context";
	
	public static final String USER = "user";
	
	public static final String TYPE = "type";
	
	public static final String PROPERTY = "property";
	
	public static final String PUBLIC_TYPE_DEFINITION = "public-type-def";
	
	public static final String ENTITY = "entity";
	
	public static final String RELATIONSHIP = "relationship";
	
	private static final ModelChangeEvent EMPTY = new ModelChangeEvent(new String[0]);
	
	// ----------------------------------------------------
	
	private final T payload;
	
	private final String[] types;
	
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
	public ModelChangeEvent(final T payload, final String... types) {
		this.payload = payload;
		this.types = types;
	}
	
	/**
	 * @param payload
	 * @param type
	 */
	public ModelChangeEvent(final String... types) {
		this(null, types);
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
	public String[] getTypes() {
		return types;
	}
	
	public boolean isAbout(final String type) {
		for (String current : types) {
			if (Infra.equals(current, type)) {
				return true;
			}
		}
		return false;
	}

}
