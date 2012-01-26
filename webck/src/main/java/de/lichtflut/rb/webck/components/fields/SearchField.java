/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.model.IModel;
import org.odlabs.wiquery.ui.autocomplete.Autocomplete;

import de.lichtflut.rb.core.RBSystem;

/**
 * <p>
 *  Search field.
 * </p>
 *
 * <p>
 * 	Created Dec 16, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SearchField extends Autocomplete<String> {

	/**
	 * @param id
	 * @param model
	 */
	public SearchField(final String id, final IModel<String> model) {
		super(id, model);
		setSource(EntityPickerField.findEntity(RBSystem.ENTITY));
	}

}
