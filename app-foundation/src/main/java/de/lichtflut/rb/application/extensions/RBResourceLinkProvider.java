/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.extensions;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.common.CommonParams;
import de.lichtflut.rb.webck.browsing.ResourceLinkProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;

/**
 * <p>
 * Glasnost specific {@link ResourceLinkProvider}
 * </p>
 * 
 * <p>
 * Created Feb 1, 2012
 * </p>
 * 
 * @author Oliver Tigges
 */
public class RBResourceLinkProvider implements ResourceLinkProvider {

	@Override
	public String getUrlToResource(final ResourceID id, final VisualizationMode vis, final DisplayMode mode) {
		final PageParameters params = new PageParameters();
		if (DisplayMode.CREATE.equals(mode)) {
			params.add(CommonParams.PARAM_RESOURCE_TYPE, id.getQualifiedName().toURI());
		} else {
			params.add(CommonParams.PARAM_RESOURCE_ID, id.getQualifiedName().toURI());
		}
		params.add(DisplayMode.PARAMETER, mode);
		switch (vis) {
			case PERIPHERY:
				return RequestCycle.get().urlFor(RBApplication.get().getPeripheryVizPage(), params).toString();
			case HIERARCHY:
				return RequestCycle.get().urlFor(RBApplication.get().getHierarchyVizPage(), params).toString();
			case FLOW_CHART:
				return RequestCycle.get().urlFor(RBApplication.get().getFlowChartVizPage(), params).toString();
			default:
				return RequestCycle.get().urlFor(RBApplication.get().getEntityDetailPage(id), params).toString();
		}
	}

}
