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
package de.lichtflut.rb.webck.common;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

/**
 * <p>
 *  Enumeration of the common display modes.
 * </p>
 *
 * <p>
 * 	Created Jan 12, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public enum DisplayMode {

	VIEW("view"), EDIT("edit"), CREATE("create");

	// ----------------------------------------------------

	/**
	 * The page parameter to be used when putting the mode into a URI.
	 */
	public static final String PARAMETER = "mode";

	/**
	 * Get the display mode from paga parameters.
	 * @param params The parameters.
	 * @return The display mode.
	 */
	public static DisplayMode fromParams(final PageParameters params) {
		final StringValue str = params.get(PARAMETER);
		if (!str.isEmpty()) {
			if (EDIT.token.equals(str.toString())) {
				return EDIT;
			} else if (CREATE.token.equals(str.toString())) {
				return CREATE;
			}
		}
		return VIEW;
	}

	// ----------------------------------------------------

	private String token;

	private DisplayMode(final String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return token;
	}

}
