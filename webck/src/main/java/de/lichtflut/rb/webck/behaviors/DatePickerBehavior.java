/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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


	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see org.apache.wicket.behavior.AbstractBehavior#bind(org.apache.wicket.Component)
	 */
	@Override
	public void bind(final Component component) {
		component.setOutputMarkupId(true);
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.behavior.AbstractBehavior#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		response.renderJavaScriptReference(DatePickerJavaScriptResourceReference.get());
		response.renderOnLoadJavaScript("$('#" + c.getMarkupId()+ "').datepicker()");
	}

}
