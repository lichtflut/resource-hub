/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.form;

import de.lichtflut.rb.webck.behaviors.DefaultButtonBehavior;

/**
 * <p>
 *  Standard button, that will be the default button of a form.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class RBDefaultButton extends RBStandardButton {

	/**
	 * @param id
	 */
	public RBDefaultButton(String id) {
		super(id);
		add(new DefaultButtonBehavior());
	}
	
}
