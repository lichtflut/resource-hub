/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Feb 10, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class FieldLabel extends Label {

	/**
	 * @param id
	 */
	public FieldLabel(String id, FormComponent<?> fc) {
		super(id, fc.getLabel());
	}

	/**
	 * @param id
	 * @param model
	 */
	public FieldLabel(FormComponent<?> fc) {
		super(getFieldIdFor(fc), fc.getLabel());
	}
	
	// ----------------------------------------------------
	
	public static String getFieldIdFor(FormComponent<?> fc) {
		return fc.getId() + "Label";
	}

}
