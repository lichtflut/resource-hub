/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.links;

import org.apache.wicket.markup.html.link.Link;

/**
 * <p>
 *  A simple link pointing to a URL.
 * </p>
 *
 * <p>
 * 	Created Nov 18, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class CrossLink extends Link {

	private final CharSequence url;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param url The URL.
	 */
	public CrossLink(final String id, final CharSequence url) {
		super(id);
		this.url = url;
	}
	
	// ----------------------------------------------------

	@Override
	protected boolean getStatelessHint() {
		return true;
	}
	
	@Override
	public void onClick() {
		// do noting.
	}
	
	@Override
	protected CharSequence getURL() {
		return url;
	}
	
}
