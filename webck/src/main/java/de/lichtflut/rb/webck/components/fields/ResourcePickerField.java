/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import java.util.Locale;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.crypt.Base64;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;

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
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public ResourcePickerField(final String id, final IModel<ResourceID> model, final ResourceID type) {
		this(id, model, findByType(type));
	}
	
	/**
	 *Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 * @param source The source for auto completion.
	 */
	public ResourcePickerField(final String id, final IModel<ResourceID> model, final AutocompleteSource src) {
		super(id, model, toDisplayModel(model), src);
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
	
	public static AutocompleteSource findByType(final ResourceID type) {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		final StringBuilder sb = new StringBuilder(ctx + "/internal/query/values");
		if (type != null) {
			sb.append("?type=");
			sb.append(Base64.encodeBase64URLSafeString(type.getQualifiedName().toURI().getBytes()));
		}
		return new AutocompleteSource(sb.toString());
	}
	
	// ----------------------------------------------------
	
	/**
	 * @param originalModel
	 * @return A corresponding display model.
	 */
	private static IModel<String> toDisplayModel(final IModel<ResourceID> originalModel) {
		return new IModel<String>() {
			
			private String value;

			public void detach() {
			}

			public String getObject() {
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

			public void setObject(final String object) {
				value = object;
			}
		};
	}
	
}
