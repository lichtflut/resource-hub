/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.identities;

import java.util.List;
import java.util.MissingResourceException;

import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.security.RBUser;

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
	 * Constructor.
	 * @param id The wicket ID
	 * @param userModel Model of {@link RBUser}
	 * @param currentRolesModel Current user-roles
	 * @param allRolesModel All available roles
	 */
	public UserRolePanel(final String id, final IModel<RBUser> userModel, final IModel<List<String>> currentRolesModel,
			final IModel<List<String>> allRolesModel) {
		super(id);

		final Palette<String> roleSelection = new Palette<String>("roleSelection", currentRolesModel, allRolesModel,
				roleChoiceRenderer(), 10, false) {
			@Override
			protected org.apache.wicket.request.resource.ResourceReference getCSS() {
				return null;
			}
		};
		add(roleSelection);

		setOutputMarkupId(true);
	}

	private IChoiceRenderer<String> roleChoiceRenderer() {
		return new IChoiceRenderer<String>() {

			@Override
			public Object getDisplayValue(final String object) {
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
			public String getIdValue(final String object, final int index) {
				return object;
			}
		};
	}

}


