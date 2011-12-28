/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.odlabs.wiquery.core.behavior.WiQueryAbstractBehavior;
import org.odlabs.wiquery.core.javascript.JsStatement;
import org.odlabs.wiquery.ui.effects.SlideEffectJavaScriptResourceReference;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Dec 6, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SlideTransitionBehavior extends WiQueryAbstractBehavior {

	public enum Direction {
		LEFT,
		RIGHT,
		TOP,
		BOTTOM
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public JsStatement statement() {
		return new JsStatement().$(getComponent()).append(".show('slide', '', 1000)");
	}
	
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		response.renderJavaScriptReference(SlideEffectJavaScriptResourceReference.get());
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public boolean isTemporary(final Component component) {
		return true;
	}
	
}
