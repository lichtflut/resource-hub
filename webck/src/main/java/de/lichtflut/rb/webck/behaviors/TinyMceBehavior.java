/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

import wicket.contrib.tinymce.settings.TinyMCESettings;

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
public class TinyMceBehavior extends wicket.contrib.tinymce.TinyMceBehavior{

	public TinyMceBehavior(){
		this(new TinyMCESettings());
	}

	public TinyMceBehavior(TinyMCESettings settings){
		super(settings);
	}
	
	// ------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		// Copies content from TinyMce field into Component
		response.renderOnLoadJavaScript("$('#" + c.getMarkupId()+"_ifr').contents().find('#tinymce').bind('keyup', function() { " +
				"var val = $(this).html();"+
				"$('#"+ c.getMarkupId() + "').html(val);" +
				"})");
		super.renderHead(c, response);
	}
}
