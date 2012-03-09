/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;


/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Mar 9, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class VisualizationLink {
	
	private String url;
	private String resourceKey;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param mode The visualization mode.
	 * @param url The URL leading to the corresponding info vis page.
	 */
	public VisualizationLink(VisualizationMode mode, String url) {
		this.url = url;
		switch (mode) {
		case DETAILS:
			resourceKey = "global.link.detail-visualization";
			break;
		case PERIPHERY:
			resourceKey = "global.link.periphery-visualization";
			break;
		case HIERARCHY:
			resourceKey = "global.link.hierarchical-visualization";
			break;
		case FLOW_CHART:
			resourceKey = "global.link.flow-chart-visualization";
			break;
		default:
			throw new IllegalArgumentException("Unknown visualizatin mode: " + mode);
		}
		
	}
	
	// ----------------------------------------------------

	/**
	 * @return the resourceKey
	 */
	public String getResourceKey() {
		return resourceKey;
	}
	
	/**
	 * @return the url
	 */
	public String getURL() {
		return url;
	}
	
}