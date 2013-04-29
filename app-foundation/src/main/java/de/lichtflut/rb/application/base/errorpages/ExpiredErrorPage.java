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
