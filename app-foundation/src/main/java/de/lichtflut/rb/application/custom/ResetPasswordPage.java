/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.custom;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.webck.components.settings.ResetPasswordPanel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 17, 2012
 * </p>
 * @author Ravi Knox
 */
public class ResetPasswordPage extends AbstractBasePage {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResetPasswordPage(){
		this.add(new ResetPasswordPanel("resetPassword"));
		this.add(new BookmarkablePageLink("backLink", RBApplication.get().getLoginPage()));
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean needsAuthentication() {
		return false;
	}
}
