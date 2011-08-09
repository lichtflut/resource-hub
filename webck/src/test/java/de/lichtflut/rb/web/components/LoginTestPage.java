/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import org.apache.wicket.markup.html.WebPage;

import de.lichtflut.rb.web.components.registration.Login;

/**
 * This page is used to test the {@link Login}.
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
		add(new Login("login"));
	}
}
