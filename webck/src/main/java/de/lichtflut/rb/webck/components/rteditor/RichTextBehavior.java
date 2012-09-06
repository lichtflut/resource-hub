/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.rteditor;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 *  This {@link org.apache.wicket.behavior.Behavior} makes a plain html textarea to a rich text editor.
 * </p>
 *
 * <p>
 * 	Created Sep 06, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RichTextBehavior extends Behavior {

    public static ResourceReference WYSIWYG_JS =
            new JavaScriptResourceReference(RichTextBehavior.class, "jquery.wysiwyg.js");

    public static ResourceReference WYSIWYG_CSS =
            new CssResourceReference(RichTextBehavior.class, "jquery.wysiwyg.css");

    public static ResourceReference LFRB_RICH_TEXT_JS =
            new CssResourceReference(RichTextBehavior.class, "lfrb-richtext-1.0.js");


    // ----------------------------------------------------

	@Override
	public void bind(Component component){
		super.bind(component);
 		component.setOutputMarkupId(true);
	}

	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		response.renderJavaScriptReference(WYSIWYG_JS);
		response.renderJavaScriptReference(LFRB_RICH_TEXT_JS);
		response.renderCSSReference(WYSIWYG_CSS);
		response.renderOnLoadJavaScript("LFRB.RichText.init('" + c.getMarkupId() + "');");
		super.renderHead(c, response);
	}
}
