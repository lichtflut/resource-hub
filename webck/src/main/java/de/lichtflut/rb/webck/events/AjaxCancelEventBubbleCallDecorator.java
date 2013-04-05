/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.events;

import org.apache.wicket.ajax.attributes.AjaxCallListener;

/**
 * <p>
 * Stop component from propagating an event to its parents.
 * </p>
 * Created: Feb 21, 2013
 * 
 * @author Ravi Knox
 */
public class AjaxCancelEventBubbleCallDecorator extends AjaxCallListener {

	// ---------------- Constructor -------------------------

	public AjaxCancelEventBubbleCallDecorator() {
		super.onAfter(getSequence());
	}

	// ------------------------------------------------------

	private CharSequence getSequence() {
		return "e = event; if(e.stopPropagation) {e.stopPropagation();}else{e.cancelBubble = true;}";
	}

}
