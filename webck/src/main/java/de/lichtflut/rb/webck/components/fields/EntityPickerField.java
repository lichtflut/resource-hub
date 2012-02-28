/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.crypt.Base64;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.webck.models.resources.ResourceDisplayModel;

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
	 * Query any resource of type system:entity
	 * @param id The component ID.
	 * @param model The model.
	 */
	public EntityPickerField(final String id, final IModel<ResourceID> model) {
		this(id, model, RBSystem.ENTITY);
	}
	
	/**
	 * Query any resource of given type.
	 * @param id The component ID.
	 * @param model The model.
	 * @param type The type (should be a sub class of system:entity).
	 */
	public EntityPickerField(final String id, final IModel<ResourceID> model, final ResourceID type) {
		super(id, model, new ResourceDisplayModel(model), findEntity(type));
		setType(ResourceID.class);
	}

	// -----------------------------------------------------
	
	public static AutocompleteSource findEntity(final ResourceID type) {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		final StringBuilder sb = new StringBuilder(ctx + "/internal/query/entity");
		if (type != null) {
			sb.append("?type=");
			sb.append(Base64.encodeBase64URLSafeString(type.getQualifiedName().toURI().getBytes()));
		}
		return new AutocompleteSource(sb.toString());
	}
	
}
