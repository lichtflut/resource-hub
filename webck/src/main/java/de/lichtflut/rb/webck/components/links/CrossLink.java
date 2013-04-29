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
