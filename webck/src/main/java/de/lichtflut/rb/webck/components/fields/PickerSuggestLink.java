/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.odlabs.wiquery.ui.autocomplete.Autocomplete;

/**
 * <p>
 *  A suggest link/button for data picker fields.
 * </p>
 *
 * <p>
 * 	Created Dec 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class PickerSuggestLink extends AjaxLink {

	private final Autocomplete field;
	
	private boolean open;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public PickerSuggestLink(String id, Autocomplete field) {
		super(id);
		this.field = field;
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void onClick(AjaxRequestTarget target) {
		if (open) {
			field.close(target);
			open = false;
		} else {
			field.search(target, "#!*");
			open = true;
		}
	}

}
