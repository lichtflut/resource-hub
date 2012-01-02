/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.odlabs.wiquery.core.behavior.WiQueryAbstractBehavior;
import org.odlabs.wiquery.core.javascript.JsStatement;

/**
 * <p>
 *  Let's a component request the focus.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class FocusBehavior extends WiQueryAbstractBehavior {
	
	private boolean temporary = true;
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public JsStatement statement() {
		return new JsStatement().$(getComponent()).append(".focus()");
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public boolean isTemporary(Component component) {
		return temporary;
	}
	
}