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
package de.lichtflut.rb.webck.components.entity;


/**
 * <p>
 *  Link to a URL representing a special visualization.
 * </p>
 *
 * <p>
 * 	Created Mar 9, 2012
 * </p>
 *
 * @author Oliver Tigges
 * @see VisualizationMode
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
        case MAP:
            resourceKey = "global.link.map-visualization";
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