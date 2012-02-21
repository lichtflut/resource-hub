/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * <p>
 *  This {@link Behavior} adds a RichTextEditor to a HTML input-field.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class TinyMceBehavior extends Behavior{
	
	private Component component;

	@Override
	public void bind(Component component)
	{
		if (this.component != null)
			throw new IllegalStateException(
					"TinyMceBehavior can not bind to more than one component");
		super.bind(component);
 		component.setOutputMarkupId(true);
		this.component = component;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		c.setOutputMarkupId(true);
		response.renderJavaScriptReference("tinymce/tiny_mce.js");
		response.renderOnLoadJavaScript("tinyMCE.init({ mode : 'exact', elements : '" + 
				c.getMarkupId() + "', theme : 'simple' });");
		// Copies content from TinyMce field into Component
		response.renderOnLoadJavaScript("$('#" + c.getMarkupId()+"_ifr').contents().find('#tinymce').bind('blur', function() { " +
				"var val = $(this).html();"+
				"$('#"+ c.getMarkupId() + "').html(val);" +
				"})");
		super.renderHead(c, response);
	}
}
