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
package de.lichtflut.rb.application.custom;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.base.RBBasePage;
import de.lichtflut.rb.application.common.CommonParams;
import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.settings.ChangePasswordPanel;
import de.lichtflut.rb.webck.components.settings.SetUserProfilePanel;
import de.lichtflut.rb.webck.components.widgets.management.MenuManagementPanel;
import de.lichtflut.rb.webck.components.widgets.management.PerspectiveManagementPanel;
import de.lichtflut.rb.webck.models.CurrentUserModel;

/**
 * <p>
 * 	Page for displaying {@link RBUser} related information.
 * </p>
 * 
 * <p>
 * 	Created: Jan 3, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class UserProfilePage extends RBBasePage {

	/**
	 * Default constructor.
	 */
	public UserProfilePage(){
		add(createTitle());
		add(createChangeUserProfile());
		add(createchangePasswordField());

		add(new PerspectiveManagementPanel("perspectives"));

		add(new MenuManagementPanel("menu"));
	}

	// ----------------------------------------------------

	@Override
	protected boolean needsAuthentication() {
		return true;
	}

	// ----------------------------------------------------

	/**
	 * @return a {@link Label} holding the displayname for the current user.
	 */
	private Label createTitle() {
		return new Label("name", CurrentUserModel.displayNameModel());
	}

	private Component createchangePasswordField() {
		return new ChangePasswordPanel("changePassword");
	}

	/**
	 * @return a {@link Panel} holding information about the users' profile.
	 */
	private Panel createChangeUserProfile() {
		return new SetUserProfilePanel("profile", new CurrentUserModel()){

			@Override
			protected void onResourceLinkClicked(final ResourceNode node) {
				final PageParameters params = new PageParameters();
				params.add(CommonParams.PARAM_RESOURCE_TYPE, RB.PERSON);
				params.add(CommonParams.PARAM_RESOURCE_ID, node.getQualifiedName());
				setResponsePage(RBApplication.get().getEntityDetailPage(RB.PERSON), params);
			}

			@Override
			protected void jumpToResourceEditorPage(final EntityHandle handle) {
				PageParameters parameters = new PageParameters();
				parameters.add(DisplayMode.PARAMETER, DisplayMode.CREATE);
				parameters.add(CommonParams.PARAM_RESOURCE_TYPE, RB.PERSON);
				setResponsePage(RBApplication.get().getEntityDetailPage(RB.PERSON), parameters);
			}

		};
	}

}
