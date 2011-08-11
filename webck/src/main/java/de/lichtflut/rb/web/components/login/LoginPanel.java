/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.login;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.security.IAuthenticationService;
import de.lichtflut.rb.core.security.impl.User;

/**
 * This component provides a basic login panel.
 *
 * Created: Aug 9, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class LoginPanel extends Panel {

	/**
	 * Default Constructor.
	 * @param id - wicket:id
	 * @param service - {@link IAuthenticationService} used for authentication
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LoginPanel(final String id, final IAuthenticationService service){
		super(id);
		setOutputMarkupId(true);
		User user = new User();
		Form form = new Form("form", new CompoundPropertyModel(user));
		form.add(new TextField("name"));
		form.add(new PasswordTextField("password"));
		form.add(new AjaxButton("submitButton") {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				LoginPanel.this.setEnabled(false);
				target.add(LoginPanel.this);
			}
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				// TODO Auto-generated method stub
			}
		});
		add(form);
		add(new Link("registerLink", Model.of("Register now")) {

			@Override
			public void onClick(){
				LoginPanel.this.replaceWith(new BasicRegistrationPanel(id, service));
			}
		});
	}
}
