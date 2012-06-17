/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
