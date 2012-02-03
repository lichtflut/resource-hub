/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample.extensions;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.editor.VisualizationMode;
import de.lichtflut.rb.websample.entities.EntityDetailPage;

/**
 * <p>
 *  WebSample specific resource link provider.
 * </p>
 *
 * <p>
 * 	Created Feb 1, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class WebSampleResourceLinkProvider implements ResourceLinkProvider {

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getUrlToResource(ResourceID id, VisualizationMode visMode, DisplayMode displayMode) {
		final PageParameters params = new PageParameters();
		if (DisplayMode.CREATE.equals(displayMode)) {
			params.add(EntityDetailPage.PARAM_RESOURCE_TYPE, id.getQualifiedName().toURI());
		} else {
			params.add(EntityDetailPage.PARAM_RESOURCE_ID, id.getQualifiedName().toURI());
		}
		params.add(DisplayMode.PARAMETER, displayMode);
		return RequestCycle.get().urlFor(EntityDetailPage.class, params).toString();
	}

}
