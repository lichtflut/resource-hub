/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.identities.UserCreationPanel;
import de.lichtflut.rb.webck.components.identities.UserSearchPanel;

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
public class CreateDomainAdminDialog extends AbstractRBDialog {
	
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
					close(AjaxRequestTarget.get());
				} catch (RBAuthException e) {
					error(e.getMessage());
				}
			}
			
			@Override
			public void onCancel() {
				close(AjaxRequestTarget.get());
			}
		});
		
		setModal(true);
	}
	
	// ----------------------------------------------------
	
	private void userSelectedAsDomainAdmin(final RBUser user, final RBDomain domain) {
		IModel<String> msgModel = new Model<String>(getString("label.confirm.add-user-as-admin"));
		DialogHoster hoster = findParent(DialogHoster.class);
		hoster.openDialog(new ConfirmationDialog(hoster.getDialogID(), msgModel) {
			@Override
			public void onConfirm() {
				addUserToDomain(user, domain);
			}
		});
	}
	
	public void addUserToDomain(RBUser user, RBDomain domain) {
		try {
			authModule.getUserManagement().grantAccessToDomain(user, domain);
			securityService.makeDomainAdmin(domain, user);
		} catch (RBAuthException e) {
			logger.error("Failed to add user '" + user.getEmail() + "' to domain " + domain, e);
			error(getString("error.add-user-failed"));
		}
	}
	
}
