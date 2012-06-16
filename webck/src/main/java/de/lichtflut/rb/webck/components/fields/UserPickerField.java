/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

import de.lichtflut.rb.core.security.RBUser;

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
public class UserPickerField extends DataPickerField<RBUser> {

	/**
	 * Query users.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public UserPickerField(final String id, final IModel<RBUser> model) {
		super(id, model, findUser());
		setType(RBUser.class);
	}
	
	// -----------------------------------------------------
	
	public static AutocompleteSource findUser() {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		final StringBuilder sb = new StringBuilder(ctx + "/service/query/users");
		return new AutocompleteSource(sb.toString());
	}
	
}
