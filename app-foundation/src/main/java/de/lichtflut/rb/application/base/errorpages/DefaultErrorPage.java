/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
