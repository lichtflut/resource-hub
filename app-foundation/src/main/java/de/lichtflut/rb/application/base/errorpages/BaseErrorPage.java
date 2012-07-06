/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base.errorpages;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.http.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.application.base.RBBasePage;

/**
 * 
 * <p>
 *  Default error page.
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class BaseErrorPage extends RBBasePage {

	private final Logger logger = LoggerFactory.getLogger(BaseErrorPage.class);
	
	// -----------------------------------------------------

	public BaseErrorPage() {
	}
	
	public BaseErrorPage(final Throwable t) {
		logger.error("unexpected error will be displayed to user", t);
	}
	
	// ----------------------------------------------------
	
	@Override
	protected void configureResponse(WebResponse response) {
		super.configureResponse(response);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	
	@Override
	public final boolean isErrorPage() {
		return true;
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected boolean needsAuthentication() {
		return false;
	}
	
}
