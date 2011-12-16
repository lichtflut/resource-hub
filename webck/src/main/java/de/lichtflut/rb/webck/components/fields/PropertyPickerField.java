/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

/**
 * <p>
 * 	Picker field for rdf:Properties.
 * </p>
 *
 * <p>
 * 	Created Dec 14, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PropertyPickerField extends DataPickerField<ResourceID> {

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public PropertyPickerField(final String id, final IModel<ResourceID> model) {
		super(id, model, findProperty());
		setType(ResourceID.class);
	}
	
	// -----------------------------------------------------
	
	public static AutocompleteSource findProperty() {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		return new AutocompleteSource(ctx +"/internal/query/property");
	}
	
}
