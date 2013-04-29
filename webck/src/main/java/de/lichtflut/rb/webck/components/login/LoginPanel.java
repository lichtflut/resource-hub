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
package de.lichtflut.rb.webck.components.login;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.webck.components.fields.FieldLabel;

/**
 * <p>
 *  Standard login page for RB applications.
 * </p>
 * 
 * <p>
 * Created Dec 8, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public abstract class LoginPanel extends Panel {


	/**
	 * Constructor.
	 */
	public LoginPanel(final String id) {
		super(id);

		add(createLoginForm());
	}

	// ----------------------------------------------------

	public abstract void onLogin(LoginData loginData);

	// ----------------------------------------------------

	protected Form<LoginData> createLoginForm() {
		final LoginData loginData = new LoginData();
		final Form<LoginData> form = new Form<LoginData>("form", new CompoundPropertyModel<LoginData>(loginData));

		form.add(new FeedbackPanel("feedback"));

		final TextField<String> idField = new TextField<String>("loginID");
		idField.setRequired(true);
		idField.setLabel(new ResourceModel("label.id"));
		form.add(idField, new FieldLabel(idField));

		final PasswordTextField passwordField = new PasswordTextField("password");
		passwordField.setRequired(true);
		passwordField.setLabel(new ResourceModel("label.password"));
		form.add(passwordField, new FieldLabel(passwordField));

		form.add(new CheckBox("stayLoggedIn"));

		final Button button = new Button("login") {
			@Override
			public void onSubmit() {
				onLogin(loginData);
				loginData.setPassword(null);
			}
		};
		form.add(button);
		form.setDefaultButton(button);
		return form;
	}

}
