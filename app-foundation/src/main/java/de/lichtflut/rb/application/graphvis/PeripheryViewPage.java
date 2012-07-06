/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.graphvis;

import org.apache.wicket.Component;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.webck.components.infovis.periphery.PeripheryVisualizationPanel;
import de.lichtflut.rb.webck.models.resources.ResourceLoadModel;

/**
 * <p>
 *  Page for a tree view of graph data.
 * </p>
 *
 * <p>
 * 	Created Dec 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PeripheryViewPage extends AbstractInfoVisPage {
	
	/**
	 * @param parameters
	 */
	public PeripheryViewPage(final PageParameters parameters) {
		super(parameters);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected Component createInfoVisPanel(String componentID, ResourceID resource) {
		return new PeripheryVisualizationPanel(componentID, new ResourceLoadModel(resource));
	}

}