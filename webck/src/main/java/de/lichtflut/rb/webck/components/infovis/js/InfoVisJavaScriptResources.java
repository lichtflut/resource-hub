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
package de.lichtflut.rb.webck.components.infovis.js;

import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 *  Collection of Java Script Resource References for information visualization.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class InfoVisJavaScriptResources {
	
	public static ResourceReference RAPHAEL_JS = 
			new JavaScriptResourceReference(InfoVisJavaScriptResources.class, "raphael-2.1.0.js");

    public static ResourceReference D3_JS =
            new JavaScriptResourceReference(InfoVisJavaScriptResources.class, "d3.v3.min.js");
	
	public static ResourceReference JIT_JS = 
			new JavaScriptResourceReference(InfoVisJavaScriptResources.class, "jit-2.0.1-min.js");
	
	public static ResourceReference INFOVIS_JS = 
			new JavaScriptResourceReference(InfoVisJavaScriptResources.class, "infovis-0.1.js");
	
	public static ResourceReference FLOWCHART_JS = 
			new JavaScriptResourceReference(InfoVisJavaScriptResources.class, "flowchart-0.1.js");
	
	public static ResourceReference PERIPHERY_JS = 
			new JavaScriptResourceReference(InfoVisJavaScriptResources.class, "periphery-0.1.js");
	
	public static ResourceReference HIERARCHY_JS = 
			new JavaScriptResourceReference(InfoVisJavaScriptResources.class, "hierarchy-0.2.js");

    public static ResourceReference MAP_JS =
            new JavaScriptResourceReference(InfoVisJavaScriptResources.class, "map-0.1.js");

}
