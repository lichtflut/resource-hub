/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class TinyMceBehavior extends Behavior {

	private Component component;
	
	// ------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(final Component component) {
		this.component = component;
		this.component.setOutputMarkupId(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final Component c, final IHeaderResponse response) {
		response.renderJavaScriptReference("resources/tinymce/tiny_mce/tiny_mce.js");
		response.renderOnLoadJavaScript("tinyMCE.init({	mode : 'textareas'	});");
		response.renderOnLoadJavaScript("$('#" + component.getMarkupId()+"_ifr').contents().find('#tinymce').bind('blur', function() { " +
					"var val = $(this).html();"+
					"$('#"+ component.getMarkupId() + "').html(val);" +
					"alert($('#" + component.getMarkupId()+"_ifr').contents().find('#tinymce').text() +' TEXT')" +
				"})");
	}

}
