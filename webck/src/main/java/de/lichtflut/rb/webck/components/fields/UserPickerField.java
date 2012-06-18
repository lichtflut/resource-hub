/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.arastreju.sge.naming.QualifiedName;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

/**
 * <p>
 * 	Picker field for users from auth module.
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class UserPickerField extends DataPickerField<QualifiedName> {

	/**
	 * Query users.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public UserPickerField(final String id, final IModel<QualifiedName> model) {
		super(id, model, findUser());
		setType(QualifiedName.class);
	}
	
	// -----------------------------------------------------
	
	public static AutocompleteSource findUser() {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		final StringBuilder sb = new StringBuilder(ctx + "/service/query/users");
		return new AutocompleteSource(sb.toString());
	}
	
}
