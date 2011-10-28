/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

/**
 * <p>
 * 	Picker field for general resource IDs.
 * </p>
 *
 * <p>
 * 	Created Oct 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityPickerField extends DataPickerField<ResourceID> {

	/**
	 * @param id
	 * @param model
	 * @param source
	 */
	public EntityPickerField(final String id, final IModel<ResourceID> model) {
		super(id, model, findByURI());
	}

	// -----------------------------------------------------
	
	public static AutocompleteSource findByURI() {
		return new AutocompleteSource("internal/query");
	}
	
}
