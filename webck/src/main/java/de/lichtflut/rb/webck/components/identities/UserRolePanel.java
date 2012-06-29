/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.identities;

import de.lichtflut.rb.core.security.RBUser;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.MissingResourceException;

/**
 * <p>
 *  Panel for selecting and viewing the roles of a user.
 * </p>
 *
 * <p>
 * 	Created Jan 12, 2012
 * </p>
 * 
 * @author Erik Aderhold
 */
public class UserRolePanel extends Panel {
	
	private final Logger logger = LoggerFactory.getLogger(UserRolePanel.class);
	
	/**
	 * @param id The wicket ID.
	 * @param currentRolesModel 
	 */
	public UserRolePanel(final String id, final IModel<RBUser> userModel, IModel<List<String>> currentRolesModel,
			IModel<List<String>> allRolesModel) {
		super(id);
		
		final Palette<String> roleSelection = new Palette<String>("roleSelection", currentRolesModel, allRolesModel,
				roleChoiceRenderer(), 10, false) {
			protected org.apache.wicket.request.resource.ResourceReference getCSS() {
				return null;
			};
		};
		add(roleSelection);
		
		setOutputMarkupId(true);
	}

	private IChoiceRenderer<String> roleChoiceRenderer() {
		return new IChoiceRenderer<String>() {
			
			@Override
			public Object getDisplayValue(String object) {
				try {
					String string = getString(object);
					return string;
				} catch (MissingResourceException e) {
					// return the object if resource is missing!
					logger.warn("Missing Resource String for role: " +object);
					return object;
				}
			}
			
			@Override
			public String getIdValue(String object, int index) {
				return object;
			}
		};
	}
	
}


