/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.crypt.Base64;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

import de.lichtflut.rb.core.entity.RBEntity;

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
	public EntityPickerField(final String id, final IModel<ResourceID> model, final ResourceID type) {
		super(id, model, toDisplayModel(model), findEntity(type));
		setType(ResourceID.class);
	}

	// -----------------------------------------------------
	
	public static AutocompleteSource findEntity(final ResourceID type) {
		final StringBuilder sb = new StringBuilder("internal/query/entity");
		if (type != null) {
			sb.append("?type=");
			sb.append(Base64.encodeBase64URLSafeString(type.getQualifiedName().toURI().getBytes()));
		}
		return new AutocompleteSource(sb.toString());
	}
	
	/**
	 * @param model
	 * @return
	 */
	private static IModel<String> toDisplayModel(final IModel<?> model) {
		return new IModel<String>() {

			public void detach() {
			}

			public String getObject() {
				if (model.getObject() == null) {
					return "";
				} 
				final Object obj = model.getObject();
				if (obj instanceof RBEntity) {
					return ((RBEntity) obj).getLabel();
				} else {
					return obj.toString();
				}
			}

			public void setObject(String object) {
			}
		};
	}
}
