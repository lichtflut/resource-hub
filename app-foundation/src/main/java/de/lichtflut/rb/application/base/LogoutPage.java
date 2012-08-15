/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base;

import org.apache.wicket.protocol.http.WebSession;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.webck.common.RBWebSession;

/**
 * <p>
 *  Logout page.
 * </p>
 *
 * <p>
 * 	Created Apr 13, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class LogoutPage extends AbstractBasePage {

	/**
	 * Constructor.
	 */
	public LogoutPage() {
		if (WebSession.exists()) {
			RBWebSession.get().onLogout();
			WebSession.get().invalidate();
		}

		setResponsePage(RBApplication.get().getLoginPage());
	}

}
