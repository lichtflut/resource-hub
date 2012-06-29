/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;


import de.lichtflut.rb.webck.models.resources.ResourceDisplayModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.crypt.Base64;
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
	public ResourcePickerField(final String id, final IModel<ResourceID> model, final ResourceID type) {
		this(id, model, findByType(type));
	}
	
	/**
	 *Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 * @param src The source for auto completion.
	 */
	public ResourcePickerField(final String id, final IModel<ResourceID> model, final AutocompleteSource src) {
		super(id, model, new ResourceDisplayModel(model), src);
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
	
}
