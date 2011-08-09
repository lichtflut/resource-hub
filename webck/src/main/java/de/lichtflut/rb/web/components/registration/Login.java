/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.registration;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * This component provides a basic login panel.
 *
 * Created: Aug 9, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class Login extends Panel {

	/**
	 * Default Constructor.
	 * @param id - wicket:id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Login(final String id){
		super(id);
		Form form = new Form("form");
		form.add(new TextField("username", new Model("")));
		form.add(new PasswordTextField("password", new Model("")));
		form.add(new AjaxButton("submitButton") {

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				// TODO Auto-generated method stub
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
				getParent().replaceWith(new BasicRegistration(id));
			}
		});
	}
}
