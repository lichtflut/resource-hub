/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.crypt.Base64;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

import de.lichtflut.rb.core.entity.RBEntityReference;

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
public class EntityPickerField extends DataPickerField<RBEntityReference> {

	/**
	 * @param id
	 * @param model
	 * @param source
	 */
	public EntityPickerField(final String id, final IModel<RBEntityReference> model, final ResourceID type) {
		super(id, model, toDisplayModel(model), findEntity(type));
		setType(RBEntityReference.class);
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
	 * @param original
	 * @return A corresponding display model.
	 */
	private static IModel<String> toDisplayModel(final IModel<RBEntityReference> original) {
		return new IModel<String>() {
			
			private String value;

			public void detach() {
			}

			public String getObject() {
				if (value == null) {
					if (original.getObject() != null) {
						value = original.getObject().toString();
					} else {
						value = "";
					}
				}
				return value;
			}

			public void setObject(final String object) {
				value = object;
			}
		};
	}
}
