/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.behaviors;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
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
public class CssModifier {

	public static Behavior setClass(final String cssClass) {
		return new AttributeModifier("class", cssClass);
	}
	
	public static Behavior setClass(final IModel<String> cssClass) {
		return new AttributeModifier("class", cssClass);
	}
	
	public static Behavior appendClass(final IModel<String> cssClass) {
		return new AttributeAppender("class", cssClass, " ");
	}

    public static Behavior appendStyle(String style) {
        return new AttributeAppender("style", Model.of(style), ";");
    }
	
}
