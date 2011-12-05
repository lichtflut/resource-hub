/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.links;

import org.apache.commons.lang3.Validate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class LabeledLink extends Panel {
	
	public static final String LINK_ID = "link";
	
	public static final String LABEL_ID = "label";
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param link The link.
	 * @param label The links label.
	 */
	public LabeledLink(final String id, final AbstractLink link, final IModel<String> label) {
		super(id, label);
		Validate.isTrue(LINK_ID.equals(link.getId()), "The link of a LabeledLink must have 'link' as compontent ID");
		link.add(new Label(LABEL_ID, label));
		add(link);
	}
	
	/**
	 * Technical constructor for subclasses. These have to care that link and label are set.
	 * @param id The component ID.
	 */
	protected LabeledLink(final String id) {
		super(id);
	}

}
