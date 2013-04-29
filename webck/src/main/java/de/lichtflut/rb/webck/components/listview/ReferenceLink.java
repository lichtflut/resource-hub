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
package de.lichtflut.rb.webck.components.listview;


import de.lichtflut.rb.webck.components.links.LabeledLink;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Link for referencing a resource page.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ReferenceLink extends LabeledLink {
	
	public static final String PARAM_RESOURCE_ID = "rid";
	
	// ----------------------------------------------------

	/**
	 * Creates a bookmarkable link with given label.
	 * @param id The ID.
	 * @param pageClass The class of the target page.
	 * @param label The link's label.
	 */
	public ReferenceLink(final String id, final Class<? extends Page> pageClass, final IModel<String> label) {
		this(id, pageClass, new PageParameters(), label);
	}
	
	/**
	 * Creates a bookmarkable link with given label and params.
	 * @param id The ID.
	 * @param pageClass The class of the target page.
	 * @param params The page parameters.
	 * @param label The link's label.
	 */
	public ReferenceLink(final String id, final Class<? extends Page> pageClass, final PageParameters params, final IModel<String> label) {
		super(id);
		addLink(pageClass, label, params);
	}
	
	/**
	 * Creates a bookmarkable link with given label, that will contain the resource's URI as param 'rid'.
	 * @param pageClass The class of the target page.
	 * @param ref The referenced ResourceID.
	 * @param label The link's label.
	 */
	public ReferenceLink(final String id, final Class<? extends Page> pageClass, final ResourceID ref, final IModel<String> label) {
		super(id);
		final PageParameters params = new PageParameters();
		params.add(PARAM_RESOURCE_ID, ref.getQualifiedName().toURI());
		
		addLink(pageClass, label, params);
	}

	// ----------------------------------------------------
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addLink(final Class<? extends Page> pageClass, final IModel<String> label, final PageParameters params) {
		final Link link = new BookmarkablePageLink(LINK_ID, pageClass, params);
		link.add(new Label(LABEL_ID, label));
		add(link);
	}
	
	
}
