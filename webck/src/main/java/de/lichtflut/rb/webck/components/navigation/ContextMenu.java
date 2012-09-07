/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
	 * @param id The component ID.
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
