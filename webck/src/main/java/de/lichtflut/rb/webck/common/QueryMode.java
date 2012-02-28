/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

/**
 * <p>
 *  Enumeration of query modes.
 * </p>
 *
 * <p>
 * 	Created Feb 28, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public enum QueryMode {
	
	UNKNOWN(null),
	
	URI("uri"),
	
	VALUES("values"),
	
	RELATION("relation"),
	
	ENTITY("entity"),
	
	PROPERTY("property"),
	
	SUB_CLASS("subclass");
	
	// ----------------------------------------------------
	
	private final String path;
	
	// ----------------------------------------------------

	private QueryMode(String path) {
		this.path = path;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the uri
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @return the uri
	 */
	public String getPath(String context) {
		return context + path;
	}

}
