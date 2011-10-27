/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.odlabs.wiquery.ui.datepicker.DatePickerJavaScriptResourceReference;

/**
 * <p>
 *  [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created May 25, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class DatePickerBehavior extends Behavior {

	private Component component;

	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see org.apache.wicket.behavior.AbstractBehavior#bind(org.apache.wicket.Component)
	 */
	@Override
	public void bind(final Component component) {
		this.component = component;
		this.component.setOutputMarkupId(true);
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.behavior.AbstractBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		response.renderJavaScriptReference(DatePickerJavaScriptResourceReference.get());
		response.renderOnLoadJavaScript("$('#" + component.getMarkupId()+ "').datepicker()");
	}

}
