/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.AttributeModifier;
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
public class CssModifier extends AttributeModifier {

	public static CssModifier setClass(final String cssClass) {
		return new CssModifier("class", cssClass);
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param attribute
	 * @param addAttributeIfNotPresent
	 * @param replaceModel
	 */
	public CssModifier(final String attribute, final String value) {
		super(attribute, new Model<String>(value));
	}

}
