/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.listview;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.IAjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.components.links.LabeledLink;

/**
 * <p>
 *  Action link.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ActionLink extends LabeledLink implements IAjaxLink {

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param label The link's label.
	 */
	public ActionLink(final String id, final IModel<String> label) {
		super(id);
		@SuppressWarnings("rawtypes")
		final AjaxFallbackLink link = new AjaxFallbackLink(LINK_ID) {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				ActionLink.this.onClick(target);
			}
		};
		link.add(new Label(LABEL_ID, label));
		link.add(TitleModifier.title(label));
		add(link);
	}
	
	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void onClick(AjaxRequestTarget target);
	
}
