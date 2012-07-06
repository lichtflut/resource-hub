/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base.errorpages;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.http.WebResponse;

/**
 * 
 * <p>
 *  Error page for session expires.
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class ExpiredErrorPage extends BaseErrorPage {

	public ExpiredErrorPage() {
	}
	
	@Override
	protected void configureResponse(WebResponse response) {
		super.configureResponse(response);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}
	
}
