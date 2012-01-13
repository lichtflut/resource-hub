/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

/**
 * <p>
 *  Enumeration of the common display modes.
 * </p>
 *
 * <p>
 * 	Created Jan 12, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public enum DisplayMode {
	
	VIEW, EDIT, CREATE;
	
	// ----------------------------------------------------
	
	/**
	 * The page parameter to be used when putting the mode into a URI.
	 */
	public static final String PARAMETER = "mode";
}
