/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.identities;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.webck.components.fields.UserPickerField;

/**
 * <p>
 *  Panel for listing of users.
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class UserSearchPanel extends Panel {
	
	/**
	 * Constructor.
	 * @param id The wicket ID.
	 */
	public UserSearchPanel(String id) {
		super(id);
		
		final IModel<RBUser> model = new Model<RBUser>();
		
		add(new UserPickerField("userpicker", model));
		
	}

}
