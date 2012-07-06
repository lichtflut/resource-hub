/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base.errorpages;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.http.WebResponse;

/**
 * 
 * <p>
 *  404 error page.
 * </p>
 *
 * <p>
 * 	Created Dec 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class PageNotFoundErrorPage extends BaseErrorPage {

	public PageNotFoundErrorPage() { }
	
	// ----------------------------------------------------
	
	@Override
	protected void configureResponse(WebResponse response) {
		super.configureResponse(response);
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}
	
}
