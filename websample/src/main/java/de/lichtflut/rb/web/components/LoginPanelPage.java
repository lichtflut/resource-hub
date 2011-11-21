/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import de.lichtflut.rb.core.security.impl.MockLoginService;
import de.lichtflut.rb.web.RBBasePage;
import de.lichtflut.rb.webck.components.login.LoginPanel;

/**
 * [TODO Insert description here.
 *
 * Created: Aug 11, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class LoginPanelPage extends RBBasePage {

	/**
	 * Constructor.
	 */
	public LoginPanelPage() {
		super("login");
		add(new LoginPanel("loginPanel", new MockLoginService()));
	}

}
