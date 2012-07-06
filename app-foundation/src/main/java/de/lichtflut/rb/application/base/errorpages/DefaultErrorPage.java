/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base.errorpages;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import de.lichtflut.rb.application.pages.AbstractBasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.http.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class DefaultErrorPage extends AbstractBasePage {

	private final Logger logger = LoggerFactory.getLogger(DefaultErrorPage.class);
	
	// -----------------------------------------------------

	public DefaultErrorPage() {
		add(new Label("detail", ""));
	}
	
	public DefaultErrorPage(final Throwable t) {
		logger.error("unexpected error will be displayed to user", t);
		
		final StringWriter stringWriter = new StringWriter();
		t.printStackTrace(new PrintWriter(stringWriter));
		
		add(new Label("detail", stringWriter.toString()));
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
