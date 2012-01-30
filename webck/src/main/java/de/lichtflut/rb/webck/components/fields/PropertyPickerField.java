/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;

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
		super(id, model, toDisplayModel(model), findProperty());
		setType(ResourceID.class);
	}
	
	// -----------------------------------------------------
	
	public static AutocompleteSource findProperty() {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		return new AutocompleteSource(ctx +"/internal/query/property");
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param originalModel
	 * @return A corresponding display model.
	 */
	private static IModel<String> toDisplayModel(final IModel<ResourceID> originalModel) {
		return new AbstractLoadableModel<String>() {
			
			private String value;

			public void setObject(final String object) {
				value = object;
			}

			@Override
			public String load() {
				final ResourceID orig = originalModel.getObject();
				if (orig == null) {
					value = null;
					return "";
				} else if (value == null) {
					final ResourceID id = originalModel.getObject();
					value = ResourceLabelBuilder.getInstance().getLabel(id, Locale.getDefault());
				} 
				return value;
			}
		};
	}
}
