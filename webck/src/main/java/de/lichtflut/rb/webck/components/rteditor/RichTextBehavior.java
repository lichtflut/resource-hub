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
package de.lichtflut.rb.webck.components.rteditor;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 *  This {@link org.apache.wicket.behavior.Behavior} makes a plain html text area to a rich text editor.
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
	public RichTextBehavior(final Type type) {
		this.type = type;
	}

	// ----------------------------------------------------

	@Override
	public void bind(final Component component){
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
				response.render(OnDomReadyHeaderItem.forScript("LFRB.RichText.standard('#" + c.getMarkupId() + "');"));
				break;
			case FULL_FEATURED:
				response.render(OnDomReadyHeaderItem.forScript("LFRB.RichText.fullFeatured('#" + c.getMarkupId() + "');"));
				break;
			default:
				break;
		}
	}
}
