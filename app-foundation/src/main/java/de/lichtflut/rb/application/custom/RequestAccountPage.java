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
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.identities.UserCreationPanel;

/**
 * <p>
 *  Page to request a new user account.
 * </p>
 *
 * <p>
 * 	Created Jan 27, 2012
 * </p>
 * @author Erik Aderhold
 */
public class RequestAccountPage extends AbstractBasePage {

	@SpringBean
	private SecurityService securityService;

	// ---------------- Constructor -------------------------

	public RequestAccountPage() {
		final UserCreationPanel registerPanel = new UserCreationPanel("registerPanel") {
			@Override
			public void onCreate(final String userID, final String username, final String password) {
				try {
					securityService.createUser(userID, username, password, getLocale());
					info(getString("global.message.user-created"));
					for (Component component : visitChildren()) {
						if(component instanceof RBStandardButton) {
							if(!(component instanceof RBCancelButton)) {
								component.setVisible(false);
							}
						} else {
							if(component instanceof TextField) {
								component.setEnabled(false);
							}
						}
					}
				} catch (RBException e) {
					if(e.getErrorCode() == ErrorCodes.SECURITY_EMAIL_ALREADY_IN_USE) {
						error(getString("global.message.duplicate.ID"));
					} else if (e.getErrorCode() == ErrorCodes.SECURITY_EMAIL_ALREADY_IN_USE){
						error(getString("global.message.duplicate.username"));
					} else if (e.getErrorCode() == ErrorCodes.EMAIL_SERVICE_EXCEPTION){
						error(getString("error.send.email"));
					}
				}
				RBAjaxTarget.add(this);
			}

			@Override
			public void onCancel() {
				setResponsePage(RBApplication.get().getLoginPage());
			}
		};
		this.add(registerPanel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean needsAuthentication() {
		return false;
	}
}
