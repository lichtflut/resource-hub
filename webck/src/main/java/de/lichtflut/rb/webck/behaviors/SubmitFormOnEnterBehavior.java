/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.odlabs.wiquery.core.behavior.WiQueryAbstractAjaxBehavior;
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
public class SubmitFormOnEnterBehavior extends WiQueryAbstractAjaxBehavior {
	
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
	 * @param submittingComponent
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
		response.render(JavaScriptHeaderItem.forReference(REF));
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