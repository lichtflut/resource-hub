/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.login;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import de.lichtflut.rb.core.security.IAuthenticationService;

/**
 * This panel provides a logout possibility for authenticated users.
 *
 * Created: Aug 11, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class LogoutPanel extends Panel {

	/**
	 * Default Constructor.
	 * @param id - wicket:id
	 * @param service - {@link IAuthenticationService}
	 */
	@SuppressWarnings("rawtypes")
	public LogoutPanel(final String id, final IAuthenticationService service) {
		super(id);
		setOutputMarkupId(true);
		Form form =  new Form("form");
		form.add(new AjaxButton("submitButton") {

			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				service.logoutUser();
				target.add(LogoutPanel.this.replaceWith(new LoginPanel(id, service)));
			}

			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				// TODO Auto-generated method stub
			}
		});
		add(form);
	}

}
