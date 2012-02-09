/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.arastreju.sge.security.User;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.form.RBStandardButton;

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
	
	@SpringBean
	private ServiceProvider provider;

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
		
		form.add(new FeedbackPanel("feedback"));
		form.add(createInputField("name"));
		form.add(createButton("button"));
		
		this.add(form);
	}

	// ------------------------------------------------------

	/**
	 * @param string
	 * @return
	 */
	private AjaxButton createButton(String id) {
		AjaxButton button = new RBStandardButton(id) {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form){
				resetPassword();
				RBAjaxTarget.add(form);
			}

		};
		return button;
	}
	
	protected void resetPassword() {
		User user = provider.getArastejuGate().getIdentityManagement().findUser(emailModel.getObject());
		if(user == null) {
			error(getString("message.no-user-found"));
		} else {
			provider.getSecurityService().resetPasswordForUser(user);
			info(getString("message.password-changed"));
		}
	}

	private TextField<String> createInputField(String id) {
		TextField<String> emailTField = new TextField<String>("email", emailModel);
		emailTField.add(EmailAddressValidator.getInstance());
		emailTField.setRequired(true);
		return emailTField;
	}

}
