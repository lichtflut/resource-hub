/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * <p>
 *  This Panel allows a user to reset his password.
 * </p>
 *
 * <p>
 * 	Created Jan 17, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class ResetPasswordPanel extends Panel {

	private IModel<String> emailModel;

	// ---------------- Constructor -------------------------

	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public ResetPasswordPanel(String id) {
		super(id);
		this.emailModel = Model.of("");
		Form form = new Form("form");
		
		TextField<String> emailTField = new TextField<String>("email", emailModel);
		
		form.add(emailTField);
		this.add(form);
	}

}
