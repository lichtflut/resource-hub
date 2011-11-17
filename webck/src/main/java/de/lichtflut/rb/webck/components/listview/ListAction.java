/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.listview;

/**
 * <p>
 *  Enumeration of standard actions on lists.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public enum ListAction {

	VIEW,
	EDIT,
	DELETE,
	CUSTOM;
	
	static ListAction forName(final String action) {
		if (VIEW.name().equals(action)) {
			return VIEW;
		} else if (EDIT.name().equals(action)) {
			return EDIT;
		} else if (DELETE.name().equals(action)) {
			return DELETE;
		}
		return CUSTOM;
	}
	
}
