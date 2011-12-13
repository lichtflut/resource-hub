/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Sep 29, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EnumDropDownChoice<T extends Enum<T>> extends DropDownChoice<T> {

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public EnumDropDownChoice(final String id, final IModel<T> model, final Enum<T>... enumValues) {
		super(id, model, (List<? extends T>) Arrays.asList(enumValues));
	}
	
}