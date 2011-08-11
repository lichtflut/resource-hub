/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.login;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.security.IAuthenticationService;
import de.lichtflut.rb.core.security.impl.User;

/**
 * This component provides a basic login component.
 * Created: Aug 9, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class BasicRegistrationPanel extends Panel {

	/**
	 * Default Constructor.
	 * @param id - Wicket:id
	 * @param service . {@link IAuthenticationService}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BasicRegistrationPanel(final String id, final IAuthenticationService service) {
		super(id);
		final User user = new User();
		Form form = new Form("form", new CompoundPropertyModel(user));
		form.add(new TextField("username", new Model("")));
		form.add(new TextField("email", new Model("")));
		form.add(new PasswordTextField("password", new Model("")));
		form.add(new PasswordTextField("confirmPassword", new Model("")));
		form.add(new AjaxButton("submitButton"){

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				service.registerNewUser(user);
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				// TODO Auto-generated method stub
			}
		});
		add(form);
	}

}
