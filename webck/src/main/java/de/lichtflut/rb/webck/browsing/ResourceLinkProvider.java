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
package de.lichtflut.rb.webck.browsing;

import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.entity.VisualizationMode;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Base interface for providing application specific URLs.
 * </p>
 *
 * <p>
 * 	Created Feb 1, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ResourceLinkProvider {

	/**
	 * Get the URL pointing to a resource in a given mode.
	 * @param id The ID of the resource.
	 * @param mode The mode.
	 * @return The URL.
	 */
	String getUrlToResource(ResourceID id, VisualizationMode vis, DisplayMode mode);
	
}