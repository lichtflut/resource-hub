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
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.identities.UserCreationPanel;
import de.lichtflut.rb.webck.components.identities.UserSearchPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Modal dialog for creation of a new domain administrator.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreateDomainAdminDialog extends RBDialog {
	
	private final Logger logger = LoggerFactory.getLogger(CreateDomainAdminDialog.class);
	
	@SpringBean
	private SecurityService securityService;
	
	@SpringBean
	private AuthModule authModule;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public CreateDomainAdminDialog(final String id, final IModel<RBDomain> model) {
		super(id);
		
		setTitle(new ResourceModel("dialog.title"));
		
		add(new UserSearchPanel("searcher") {
			@Override
			public void userSelected(final RBUser user) {
				userSelectedAsDomainAdmin(user, model.getObject());
			}
		});
		
		add(new UserCreationPanel("creator") {
			@Override
			public void onCreate(String email, String username, String password) {
				try {
					securityService.createDomainAdmin(model.getObject(), email, username, password);
					close(RBAjaxTarget.getAjaxTarget());
				} catch (RBAuthException e) {
					error(e.getMessage());
				}
			}
			
			@Override
			public void onCancel() {
				close(RBAjaxTarget.getAjaxTarget());
			}
		});
		
		setModal(true);
	}
	
	// ----------------------------------------------------
	
	private void userSelectedAsDomainAdmin(final RBUser user, final RBDomain domain) {
		IModel<String> msgModel = new Model<String>(getString("label.confirm.add-user-as-admin"));
		DialogHoster hoster = findParent(DialogHoster.class);
		close(RBAjaxTarget.getAjaxTarget());
		hoster.openDialog(new ConfirmationDialog(hoster.getDialogID(), msgModel) {
			@Override
			public void onConfirm() {
				addUserToDomain(user, domain);
			}
		});
	}
	
	private void addUserToDomain(RBUser user, RBDomain domain) {
		try {
			authModule.getUserManagement().grantAccessToDomain(user, domain);
			securityService.makeDomainAdmin(domain, user);
		} catch (RBAuthException e) {
			logger.error("Failed to add user '" + user.getEmail() + "' to domain " + domain, e);
			error(getString("error.add-user-failed"));
		}
	}
	
}
