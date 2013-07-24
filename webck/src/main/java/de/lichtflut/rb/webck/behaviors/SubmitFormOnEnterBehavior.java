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
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.odlabs.wiquery.core.behavior.WiQueryAbstractBehavior;
import org.odlabs.wiquery.core.javascript.JsStatement;

/**
 * <p>
 * Submit a form if enter is pressed.
 * </p>
 * 
 * <p>
 * Created Feb 21, 2012
 * </p>
 * 
 * @author Oliver Tigges
 */
public class SubmitFormOnEnterBehavior extends WiQueryAbstractBehavior {
	
	public static final ResourceReference REF = new JavaScriptResourceReference(SubmitFormOnEnterBehavior.class, "lfrb-forms.js");

	private Component submittingComponent;

	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public SubmitFormOnEnterBehavior() {
		this(null);
	}

	/**
	 * Constructor.
	 * @param submittingComponent The button or link submitting the form.
	 */
	public SubmitFormOnEnterBehavior(Component submittingComponent) {
		setSubmittingComponent(submittingComponent);
	}
	
	// ----------------------------------------------------

	public void setSubmittingComponent(Component submittingComponent) {
		this.submittingComponent = submittingComponent;
		if (submittingComponent != null) {
			submittingComponent.setOutputMarkupId(true);	
		}
	}

	@Override
	public JsStatement statement() {
		final Form<?> form = (Form<?>) getComponent();
		final Component button = getSubmittingComponent(form);

		if (button != null) {
			final String formID = form.getMarkupId();
			final String buttonID = button.getMarkupId();
			return new JsStatement().append("LFRB.Forms.submitOnEnter('" + formID + "', '" + buttonID + "');");
		} else {
			return new JsStatement();
		}
	}
	
	// ----------------------------------------------------
	
	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		response.renderJavaScriptReference(REF);
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		getComponent().setOutputMarkupId(true);
	}
	
	// ----------------------------------------------------

	private Component getSubmittingComponent(Form<?> form) {
		if (submittingComponent != null) {
			return submittingComponent;
		} else {
			return (Component) form.getDefaultButton();
		}
	}

}