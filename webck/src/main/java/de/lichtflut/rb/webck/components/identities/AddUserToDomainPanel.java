/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.identities;

import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 *  Panel for searching user's to add them to a domain.
 * </p>
 *
 * <p>
 * 	Created Jun 22, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class AddUserToDomainPanel extends Panel {
	
	private static final Logger logger = LoggerFactory.getLogger(AddUserToDomainPanel.class);
	
	@SpringBean
	private AuthModule authModule;
	
	@SpringBean
	private ServiceContext serviceContext;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id The wicket ID.
	 */
	public AddUserToDomainPanel(String id) {
		super(id);
		
		add(new UserSearchPanel("searcher") {
			@Override
			public void userSelected(final RBUser user) {
				IModel<String> msgModel = new Model<String>(getString("label.confirm.add-user"));
				DialogHoster hoster = findParent(DialogHoster.class);
				hoster.openDialog(new ConfirmationDialog(hoster.getDialogID(), msgModel) {
					@Override
					public void onConfirm() {
						addUserToDomain(user);
					}
				});
			}
		});
	}
	
	// ----------------------------------------------------
	
	public void addUserToDomain(RBUser user) {
		String domainName = serviceContext.getDomain();
		RBDomain domain = authModule.getDomainManager().findDomain(domainName);
		try {
			authModule.getUserManagement().grantAccessToDomain(user, domain);
		} catch (RBAuthException e) {
			logger.error("Failed to add user '" + user.getEmail() + "' to domain " + domainName, e);
			error(getString("error.add-user-failed"));
		}
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.USER));
	}

}
