/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.events;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.event.IEvent;

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
	
	private final Set<String> types = new HashSet<String>();
	
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
	 * @param types
	 */
	public ModelChangeEvent(final T payload, final String... types) {
		this.payload = payload;
		for (String current : types) {
			this.types.add(current);	
		}
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
	public Collection<String> getTypes() {
		return types;
	}
	
	public boolean isAbout(final String... evtTypes) {
		for (String current : evtTypes) {
			if (types.contains(current)) {
				return true;
			}
		}
		return false;
	}

}
