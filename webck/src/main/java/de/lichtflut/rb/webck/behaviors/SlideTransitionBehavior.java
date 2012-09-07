/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.odlabs.wiquery.core.behavior.WiQueryAbstractAjaxBehavior;
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
public class SlideTransitionBehavior extends WiQueryAbstractAjaxBehavior {

	public enum Direction {
		LEFT,
		RIGHT,
		TOP,
		BOTTOM
	}
	
	// ----------------------------------------------------

	@Override
	public JsStatement statement() {
		return new JsStatement().$(getComponent()).append(".show('slide', '', 1000)");
	}
	
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(SlideEffectJavaScriptResourceReference.get()));
	}
	
	// ----------------------------------------------------
	
	@Override
	public boolean isTemporary(final Component component) {
		return true;
	}
	
}
