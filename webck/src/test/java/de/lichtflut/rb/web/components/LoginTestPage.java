/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import org.apache.wicket.markup.html.WebPage;

import de.lichtflut.rb.core.security.IAuthenticationService;
import de.lichtflut.rb.core.security.LoginData;
import de.lichtflut.rb.webck.components.login.LoginPanel;

/**
 * This page is used to test the {@link LoginPanel}.
 *
 * Created: Aug 9, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class LoginTestPage extends WebPage {

	/**
	 * Default Constructor.
	 */
	public LoginTestPage(){
		add(new LoginPanel("login", new IAuthenticationService(){

			@Override
			public boolean authenticateUser(final String username, final String password) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean registerNewUser(final LoginData user) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean logoutUser() {
				// TODO Auto-generated method stub
				return false;
			}

		}));
	}
}
