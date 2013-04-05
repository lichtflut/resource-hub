/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.events;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxPostprocessingCallDecorator;

/**
 * <p>
 * Stop component from propagating an event to its parents.
 * </p>
 * Created: Feb 21, 2013
 *
 * @author Ravi Knox
 */
public class AjaxCancelEventBubbleCallDecorator extends AjaxPostprocessingCallDecorator {

	// ---------------- Constructor -------------------------

	public AjaxCancelEventBubbleCallDecorator()
	{
		this(null);
	}

	public AjaxCancelEventBubbleCallDecorator(final IAjaxCallDecorator delegate)
	{
		super(delegate);
	}

	// ------------------------------------------------------

	@Override
	public CharSequence postDecorateScript(final Component component, final CharSequence script) {
		return "e = event; if(e.stopPropagation) {e.stopPropagation();}else{e.cancelBubble = true;}" + script;
	}

}
