/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base;

import de.lichtflut.rb.application.pages.AbstractBasePage;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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
	public LogoutPage(final PageParameters params) {
		if (WebSession.exists()) {
			RBWebSession.get().onLogout();
			WebSession.get().invalidate();
		}
		
		setResponsePage(LoginPage.class);
	}
	
}
