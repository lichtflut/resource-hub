/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.buttons;


/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class RBCancelButton extends RBStandardButton {

	/**
	 * @param id
	 */
	public RBCancelButton(String id) {
		super(id);
		setDefaultFormProcessing(false);
	}
	
}
