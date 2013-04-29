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

	private final static Logger LOGGER = LoggerFactory.getLogger(InternalErrorPage.class);
	
	// -----------------------------------------------------

	public InternalErrorPage() {
        add(new Label("message", "Unknown cause."));
		add(new Label("detail", ""));
	}
	
	public InternalErrorPage(final Throwable t) {
		LOGGER.error("unexpected error will be displayed to user", t);

        add(new Label("message", getInternalMessage(t)));

		final StringWriter stringWriter = new StringWriter();
		t.printStackTrace(new PrintWriter(stringWriter));
		
		add(new Label("detail", stringWriter.toString()));
	}

    private String getInternalMessage(final Throwable t) {
        if (t == null) {
            return "Unknown cause.";
        } else if (t.getCause() == null) {
            return t.getLocalizedMessage();
        } else {
            return getInternalMessage(t.getCause());
        }
    }
	
}
