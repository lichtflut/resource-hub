/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import org.apache.wicket.markup.html.WebPage;

import de.lichtflut.rb.core.security.IAuthenticationService;
import de.lichtflut.rb.core.security.IUser;
import de.lichtflut.rb.webck.components.login.BasicRegistrationPanel;

/**
 * This page is used to test the {@link BasicRegistrationPanel}.
 *
 * Created: Aug 9, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class BasicRegistrationTestPage extends WebPage{

	/**
	 * Default Constructor.
	 */
	public BasicRegistrationTestPage(){
		this.add(new BasicRegistrationPanel("registration", new IAuthenticationService() {
			@Override
			public boolean registerNewUser(final IUser user) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean authenticateUser(final String username, final String password) {
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
