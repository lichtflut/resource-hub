/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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

}
