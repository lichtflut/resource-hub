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
package de.lichtflut.rb.webck.components.navigation;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 *  Basic context menu, that will pop up on a specified action.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ContextMenu extends WebMarkupContainer {

	public static final ResourceReference JS_REF = 
			new JavaScriptResourceReference(ExtendedActionsPanel.class, "lfrb-contextmenu.js");
	
	// ----------------------------------------------------
	
	/**
     * Constructor.
	 * @param id The wicket ID.
	 */
	public ContextMenu(String id) {
		super(id);
		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);
	}

	// ----------------------------------------------------
	
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(ContextMenu.JS_REF));
	}
	
	// ----------------------------------------------------
	
	public Behavior createToggleBehavior(String event) {
		return new AttributeModifier(event, "LFRB.ContextMenu.toggle('#"+ getMarkupId() + "')");
	}
	
	public void close(AjaxRequestTarget target) {
		target.appendJavaScript("LFRB.ContextMenu.close('#"+ getMarkupId() + "')");
	}

}
