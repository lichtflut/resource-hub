/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.odlabs.wiquery.ui.autocomplete.Autocomplete;

/**
 * <p>
 *  A suggest link/button for data picker fields. Only to be used together with {@link DataPickerField}s.
 * </p>
 *
 * <p>
 * 	Created Dec 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class PickerSuggestLink extends Label {

	/**
	 * @param id
	 */
	protected PickerSuggestLink(String id, Autocomplete field) {
		super(id);
		add(new AttributeModifier("onclick", "LFRB.Datapicker.toggle('#"+ field.getMarkupId() + "')"));
	}
	
}
