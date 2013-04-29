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
package de.lichtflut.rb.webck.components.identities;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;

/**
 * <p>
 * Panel for editing of a user.
 * </p>
 * 
 * <p>
 * Created Dec 8, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public abstract class UserCreationPanel extends Panel {

	private final IModel<String> emailID = new Model<String>();

	private final IModel<String> username = new Model<String>();

	private final IModel<String> password = new Model<String>();
	private final IModel<String> passwordConfirm = new Model<String>();

	// ----------------------------------------------------

	/**
	 * @param id The wicket ID.
	 */
	public UserCreationPanel(final String id) {
		super(id);

		final Form<?> form = new Form<Void>("form");
		form.setOutputMarkupId(true);

		form.add(new FeedbackPanel("feedback"));

		final TextField<String> idField = new TextField<String>("id", emailID);
		idField.setRequired(true);
		idField.add(EmailAddressValidator.getInstance());
		form.add(idField);

		final TextField<String> usernameField = new TextField<String>("username", username);
		form.add(usernameField);

		final PasswordTextField passwordField = new PasswordTextField("password", password);
		form.add(passwordField);
		final PasswordTextField passwordConfirmField = new PasswordTextField("passwordConfirm", passwordConfirm);
		form.add(passwordConfirmField);
		form.add(new EqualPasswordInputValidator(passwordField, passwordConfirmField));

		form.add(createCreateButton());
		form.add(createCancelButton());

		add(form);

		setOutputMarkupId(true);
	}

	// ----------------------------------------------------

	public abstract void onCreate(String userID, String username, String password);

	public abstract void onCancel();

	// -- BUTTONS -----------------------------------------

	protected AjaxButton createCreateButton() {
		final AjaxButton create = new RBDefaultButton("create") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				RBAjaxTarget.add(form);
				onCreate(normalize(emailID.getObject()), normalize(username.getObject()), password.getObject());
			}
		};
		return create;
	}

	protected AjaxButton createCancelButton() {
		final AjaxButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				reset();
				onCancel();
				RBAjaxTarget.add(form);
			}
		};
		return cancel;
	}

	/**
	 * @param name
	 * @return
	 */
	private String normalize(final String name) {
		if (name == null) {
			return null;
		} else {
			return name.trim().toLowerCase();
		}
	}

	private void reset() {
		emailID.setObject(null);
		username.setObject(null);
		password.setObject(null);
	}

}
