/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.odlabs.wiquery.ui.datepicker.DatePickerJavaScriptResourceReference;

/**
 * <p>
 *  Bahavior for making an input field a date picker.
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

	@Override
	public void bind(final Component component) {
		component.setOutputMarkupId(true);
	}

	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(DatePickerJavaScriptResourceReference.get()));
		response.render(OnLoadHeaderItem.forScript("$('#" + c.getMarkupId() + "').datepicker()"));
	}

}
