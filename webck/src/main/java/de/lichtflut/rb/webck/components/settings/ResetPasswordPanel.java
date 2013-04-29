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
package de.lichtflut.rb.webck.components.settings;

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

import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
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

	private final IModel<String> emailModel;
	
	@SpringBean
	private SecurityService securityService;
	
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
				emailModel.setObject("");
				RBAjaxTarget.add(form);
			}

		};
		return button;
	}
	
	protected void resetPassword() {
		final String email = emailModel.getObject();
		final RBUser existing = securityService.findUser(email);
		if(existing == null) {
			error(getString("message.no-user-found"));
		} else {
			try {
				securityService.resetPasswordForUser(existing, getLocale());
				info(getString("message.password-changed"));
			} catch (RBException e) {
				error(getString("error.send.email"));
			}
		}
	}

	private TextField<String> createInputField(String id) {
		TextField<String> emailTField = new TextField<String>("email", emailModel);
		emailTField.add(EmailAddressValidator.getInstance());
		emailTField.setRequired(true);
		return emailTField;
	}

}
