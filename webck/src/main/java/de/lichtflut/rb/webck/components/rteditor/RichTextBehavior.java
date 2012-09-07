/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.rteditor;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.Model;
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

    public enum Type {
        SIMPLE,
        STANDARD,
        FULL_FEATURED
    }

    // ----------------------------------------------------

    private Type type = Type.STANDARD;

    // ----------------------------------------------------

    /**
     * Creates a new behavior with type =  Type.STANDARD.
     */
    public RichTextBehavior() {
    }

    /**
     * Creates a new behavior with given rich text editor type.
     */
    public RichTextBehavior(Type type) {
        this.type = type;
    }

    // ----------------------------------------------------

	@Override
	public void bind(Component component){
		super.bind(component);
 		component.setOutputMarkupId(true);
        component.add(new AttributeAppender("style", Model.of("visibility:hidden"), ";"));
	}

	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
        super.renderHead(c, response);
		response.render(JavaScriptHeaderItem.forReference(WYSIWYG_JS));
		response.render(JavaScriptHeaderItem.forReference(LFRB_RICH_TEXT_JS));
		response.render(CssHeaderItem.forReference(WYSIWYG_CSS));
        switch (type) {
            case SIMPLE:
                // This is a fix for RTEs displayed in modal dialogs.
                // The dialog has to be completely rendered, before the RTE starts.
                response.render(OnLoadHeaderItem.forScript("LFRB.RichText.simple('#" + c.getMarkupId() + "');"));
                break;
            case STANDARD:
                response.render(OnLoadHeaderItem.forScript("LFRB.RichText.standard('#" + c.getMarkupId() + "');"));
                break;
            case FULL_FEATURED:
                response.render(OnLoadHeaderItem.forScript("LFRB.RichText.fullFeatured('#" + c.getMarkupId() + "');"));
                break;
        }
	}
}
