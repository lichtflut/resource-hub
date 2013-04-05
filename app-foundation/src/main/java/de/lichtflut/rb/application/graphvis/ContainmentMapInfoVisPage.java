/*
 * Copyright 2013 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.graphvis;

import de.lichtflut.rb.webck.components.infovis.map.ContainmentMapVisualizationPanel;
import de.lichtflut.rb.webck.models.resources.ResourceLoadModel;
import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Page for a map view of graph data.
 * </p>
 *
 * <p>
 * 	Created Mar 15, 2013
 * </p>
 *
 * @author Oliver Tigges
 */
public class ContainmentMapInfoVisPage extends AbstractInfoVisPage {

	public ContainmentMapInfoVisPage(final PageParameters parameters) {
		super(parameters);
	}
	
	// ----------------------------------------------------
	
	@Override
	protected Component createInfoVisPanel(String componentID, ResourceID resource) {
		return new ContainmentMapVisualizationPanel(componentID, new ResourceLoadModel(resource));
	}

}
