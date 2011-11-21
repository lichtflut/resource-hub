/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.web.entities.EntityDetailPage;
import de.lichtflut.rb.webck.application.LinkProvider;

/**
 * <p>
 *  Simple LinkProvider for WebSample application.
 * </p>
 *
 * <p>
 * 	Created Nov 18, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class WebsampleLinkProvider implements LinkProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CharSequence browseToResource(final ResourceID rid) {
		final PageParameters params = new PageParameters();
		params.add(EntityDetailPage.PARAM_RESOURCE_ID, rid.getQualifiedName().toString());
		return RequestCycle.get().urlFor(EntityDetailPage.class, params);
	}

}
