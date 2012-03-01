/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 *  This {@link Behavior} adds a RichTextEditor to a HTML input-field.<br>
 *  !!!ATTENTION!!!<br>
 *  Adapted <code> tiny_mce.js</code> so that resources can be accessed using a valid URL.
 *  That file should not be changed without having a solid reason!
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class TinyMceBehavior extends Behavior{
	
	private final static ResourceReference TINY_MCE = new JavaScriptResourceReference(TinyMceBehavior.class, "tiny_mce/tiny_mce.js");
	private final static ResourceReference TINY_EN = new JavaScriptResourceReference(TinyMceBehavior.class, "tiny_mce/langs/en.js");
	private final static ResourceReference TINY_THEME = new JavaScriptResourceReference(TinyMceBehavior.class, "tiny_mce/themes/rb/editor_template.js");
	private final static ResourceReference UI_CSS = new CssResourceReference(TinyMceBehavior.class, "tiny_mce/themes/rb/skins/default/ui.css");
	private final static ResourceReference CONTENT_CSS = new CssResourceReference(TinyMceBehavior.class, "tiny_mce/themes/rb/skins/default/content.css");
	private final static ResourceReference DIALOG_CSS = new CssResourceReference(TinyMceBehavior.class, "tiny_mce/themes/rb/skins/default/dialog.css");

	
	// ------------------------------------------------------
	
	@Override
	public void bind(Component component){
		super.bind(component);
 		component.setOutputMarkupId(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		response.renderJavaScriptReference(TINY_MCE);
		response.renderJavaScriptReference(TINY_THEME);
		response.renderJavaScriptReference(TINY_EN);
		response.renderCSSReference(UI_CSS);
		response.renderCSSReference(CONTENT_CSS);
		response.renderCSSReference(DIALOG_CSS);
		response.renderOnLoadJavaScript("tinyMCE.init({ " +
					"language: 'en', " +
					"mode : 'exact'," +
					"elements : '" + c.getMarkupId() + "'," +
					"theme : 'rb'" +
				"});");
		// Copies content from TinyMce field into Component
		response.renderOnLoadJavaScript("$('#" + c.getMarkupId()+"_ifr').contents().find('#tinymce').bind('blur', function() { " +
				"var val = $(this).html();"+
				"$('#"+ c.getMarkupId() + "').html(val);" +
				"})");
		super.renderHead(c, response);
	}
}
