/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.links;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

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
public class CrossLink extends Link<String> {

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param url The URL.
	 */
	public CrossLink(final String id, final String url) {
		super(id, Model.of(url));
	}
	
	/**
	 * Constructor.
	 * @param id The ID.
	 * @param url The URL.
	 */
	public CrossLink(final String id, final IModel<String> url) {
		super(id, url);
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
		return getModelObject();
	}
	
}
