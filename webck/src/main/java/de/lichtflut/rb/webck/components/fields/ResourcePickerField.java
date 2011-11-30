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
 * 	Picker field for general resource IDs.
 * </p>
 *
 * <p>
 * 	Created Oct 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourcePickerField extends DataPickerField<ResourceID> {

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public ResourcePickerField(final String id, final IModel<ResourceID> model) {
		this(id, model, findByURI());
	}
	
	/**
	 *Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 * @param source The source for auto completion.
	 */
	public ResourcePickerField(final String id, final IModel<ResourceID> model, final AutocompleteSource src) {
		super(id, model, src);
		setType(ResourceID.class);
	}
	
	// -----------------------------------------------------
	
	public static AutocompleteSource findByURI() {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		return new AutocompleteSource(ctx +"/internal/query/uri");
	}
	
	public static AutocompleteSource findByValues() {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		return new AutocompleteSource(ctx +"internal/query/values");
	}
	
}
