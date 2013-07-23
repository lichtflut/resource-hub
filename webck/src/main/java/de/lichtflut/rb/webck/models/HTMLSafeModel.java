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
package de.lichtflut.rb.webck.models;

import org.apache.wicket.model.IModel;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import java.net.URL;

/**
 * <p>
 *  This model provides a safe way to handle Strings that might contain HTML or Javascript.
 *  <br>
 *  If an Exception is thrown while attempting to initialize the HTML-Sanitizer, it can be dealed with
 *  in the handleException() method.
 * </p>
 *
 * <p>
 * 	Created Feb 8, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class HTMLSafeModel implements IModel<String> {

	private final static String RTE_POLICY_LOCATION = "antisamy/antisamy-tinymce-1.4.4.xml";

	private IModel<String> model;
	
	// ---------------- Constructor -------------------------

	public HTMLSafeModel(IModel<String> model){
		this.model = model;
	}
	
	// ------------------------------------------------------

	@Override
	public String getObject() {
		return parseContent(model.getObject());
	}

	@Override
	public void setObject(String object) {
			this.model.setObject(parseContent(object));
	}
	
	@Override
	public void detach() {}
	
	// ------------------------------------------------------
	
	private String parseContent(String content) {
		Policy policy;
		CleanResults cr;
		if(content == null) {
			return content;
		}
		try {
			policy = getPolicy();
			cr = new AntiSamy().scan(content.toString(), policy);
			return cr.getCleanHTML();
		} catch (PolicyException e) {
			throwRBExepction("RichTextEditor Policy cannot be found");
		} catch (ScanException e) {
			throwRBExepction("Error while scanning content of RichTextEditor");
		}
		return null;
	}

	private Policy getPolicy() throws PolicyException {
		URL url = Thread.currentThread().getContextClassLoader().getResource(RTE_POLICY_LOCATION);
		return Policy.getInstance(url);
	}
	
	private void throwRBExepction(String msg) {
		throw new RuntimeException(msg);
	}

}
