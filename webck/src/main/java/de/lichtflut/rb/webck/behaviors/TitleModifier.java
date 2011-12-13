/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * <p>
 *  Simpler modifier for css stuff.
 * </p>
 *
 * <p>
 * 	Created Dec 9, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TitleModifier extends AttributeModifier {

	public static TitleModifier title(final String title) {
		return new TitleModifier(title);
	}
	
	public static TitleModifier title(final IModel<String> title) {
		return new TitleModifier(title);
	}
	
	// ----------------------------------------------------
	
	public TitleModifier(final String title) {
		super("title", new Model<String>(title));
	}
	
	public TitleModifier(final IModel<String> title) {
		super("title", title);
	}

}
