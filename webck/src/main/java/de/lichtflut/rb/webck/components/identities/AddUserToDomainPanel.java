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

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
