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
package de.lichtflut.rb.core.common;

import java.util.regex.Pattern;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jun 16, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SearchTerm {
	
	public static final Pattern ESCAPE_CHARS = Pattern.compile("[\\\\+\\-\\!\\(\\)\\:\\^\\]\\{\\}\\~\\*\\?\\s]");
	
	public static final String VERBATIM_PREFIX = "#!";
	
	// ----------------------------------------------------
	
	private String originalTerm;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param originalTerm The unescaped user input.
	 */
	public SearchTerm(String originalTerm) {
		this.originalTerm = originalTerm;
	}
	
	// ----------------------------------------------------

	/**
	 * @return the originalTerm
	 */
	public String getOriginalTerm() {
		return originalTerm;
	}
	
	public String prepareTerm() {
		if (originalTerm.startsWith(SearchTerm.VERBATIM_PREFIX)) {
			return originalTerm.substring(SearchTerm.VERBATIM_PREFIX.length());
		} else {
			return escape();	
		}
	}
	
	public String prepareTerm(float boost) {
		return prepareTerm() + "^" + boost;
	}
	
	// ----------------------------------------------------
	
	protected String escape() {
		final StringBuilder sb = new StringBuilder(128);
		if (!originalTerm.startsWith("*")) {
			sb.append("*");
		}
		sb.append(SearchTerm.ESCAPE_CHARS.matcher(originalTerm).replaceAll("\\\\$0"));
		if (!originalTerm.endsWith("*")) {
			sb.append("*");
		}
		return sb.toString();
	}

}
