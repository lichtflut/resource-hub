/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

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
	
	VIEW("view"), EDIT("edit"), CREATE("create");
	
	// ----------------------------------------------------
	
	/**
	 * The page parameter to be used when putting the mode into a URI.
	 */
	public static final String PARAMETER = "mode";
	
	/**
	 * Get the display mode from paga parameters.
	 * @param params The parameters.
	 * @return The display mode.
	 */
	public static DisplayMode fromParams(PageParameters params) {
		final StringValue str = params.get(PARAMETER);
		if (!str.isEmpty()) {
			if (EDIT.token.equals(str.toString())) {
				return EDIT;
			} else if (CREATE.token.equals(str.toString())) {
				return CREATE;
			}
		} 
		return VIEW;
	}
	
	// ----------------------------------------------------
	
	private String token;
	
	private DisplayMode(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return token;
	}
	
}
