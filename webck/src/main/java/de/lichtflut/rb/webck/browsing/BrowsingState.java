/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.browsing;

/**
 * <p>
 *  Possible browsing states.
 * </p>
 *
 * <p>
 * 	Created Dec 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public enum BrowsingState {

	/**
	 * View an entity.
	 */
	VIEW,
	
	/**
	 * Edit an entity.
	 */
	EDIT,
	
	/**
	 * Create a new entity.
	 */
	CREATE,
	
	/**
	 * Create an entity and return the reference to the initiator.
	 */
	CREATE_REFERENCE;
}
