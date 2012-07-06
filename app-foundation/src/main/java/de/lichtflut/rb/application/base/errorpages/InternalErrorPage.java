/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base.errorpages;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.wicket.markup.html.basic.Label;
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
public class InternalErrorPage extends BaseErrorPage {

	private final Logger logger = LoggerFactory.getLogger(InternalErrorPage.class);
	
	// -----------------------------------------------------

	public InternalErrorPage() {
		add(new Label("detail", ""));
	}
	
	public InternalErrorPage(final Throwable t) {
		logger.error("unexpected error will be displayed to user", t);
		
		final StringWriter stringWriter = new StringWriter();
		t.printStackTrace(new PrintWriter(stringWriter));
		
		add(new Label("detail", stringWriter.toString()));
	}
	
}
