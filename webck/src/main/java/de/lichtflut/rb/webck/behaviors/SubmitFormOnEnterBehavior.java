/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
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

	private Component submittingComponent;

	// ----------------------------------------------------
	
	/**
	 * Default Konstruktor ohne explizite Submit-Komponente.
	 */
	public SubmitFormOnEnterBehavior() {
		this(null);
	}

	public SubmitFormOnEnterBehavior(Component submittingComponent) {
		this.submittingComponent = submittingComponent;
	}
	
	// ----------------------------------------------------

	public void setSubmittingComponent(Component submittingComponent) {
		this.submittingComponent = submittingComponent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBind() {
		super.onBind();
		getComponent().setOutputMarkupId(true);
	}

	@Override
	public JsStatement statement() {
		final Form<?> form = (Form<?>) getComponent();
		final Component button = getSubmittingComponent(form);

		if (form.isRootForm() && button != null) {
			final String formID = form.getMarkupId();
			final String buttonID = button.getMarkupId();
			return new JsStatement().append("LFRB.Forms.submitOnEnter(" + "   jQuery('#" + formID + "'), '" + buttonID
					+ "'" + ");");
		} else {
			return new JsStatement();
		}
	}

	private Component getSubmittingComponent(Form<?> form) {
		if (submittingComponent != null) {
			return submittingComponent;
		} else {
			return (Component) form.getDefaultButton();
		}
	}

}