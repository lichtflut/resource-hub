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
package de.lichtflut.rb.application.admin.identitymanagment;

import de.lichtflut.rb.application.admin.AdminBasePage;
import de.lichtflut.rb.application.common.RBPermission;
import de.lichtflut.rb.application.common.RBRole;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.identities.AddUserToDomainPanel;
import de.lichtflut.rb.webck.components.identities.UserCreationPanel;
import de.lichtflut.rb.webck.components.identities.UserDetailPanel;
import de.lichtflut.rb.webck.components.identities.UserListPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Admin page for user and identity management.
 * </p>
 * 
 * <p>
 * Created Dec 8, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class IdentityManagementPage extends AdminBasePage {

	/**
	 * The default role(s) for a new created user.
	 */
	protected static final List<String> DEFAULT_ROLES = new ArrayList<String>(RBRole.values().length) {
		{
			add(RBRole.ACTIVE_USER.name());
		}
	};

	@SpringBean
	private AuthModule authModule;

	@SpringBean
	private SecurityService securityService;
	
	@SpringBean 
	private ServiceContext context;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public IdentityManagementPage() {
		
		final UserListPanel userList = new UserListPanel("userList", registeredUsersModel()) {
			@Override
			public void onUserSelected(RBUser user) {
				editUser(Model.of(user));
				
			}
		};
		add(userList);
		
		add(new AddUserToDomainPanel("searcher"));
		
		add(new WebMarkupContainer("editor").setOutputMarkupId(true));
		
		add(new AjaxLink("createLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				createUser();
			}
		});

		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				warn("Export has been deactivated.");
			}
		};
		add(exportLink);
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isAuthorized(IModel<RBUser> userModel) {
		final RBUser user = userModel.getObject();
		String domain = context.getDomain();
		return user != null && securityService.getUserPermissions(user, domain)
				.contains(RBPermission.SEE_USERS.name());
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.USER)) {
			updatePage();
		} 
	}
	
	// ----------------------------------------------------

	protected void createUser() {
		final UserCreationPanel panel = new UserCreationPanel("editor") {
			@Override
			public void onCreate(final String emailID, String username, String password) {
				try {
					RBUser registered = securityService.createUser(emailID, username, password, getLocale());
					securityService.setUserRoles(registered, context.getDomain(), DEFAULT_ROLES);
					editUser(Model.of(registered));
				} catch (RBException e) {
					if (e.getErrorCode() == ErrorCodes.SECURITY_EMAIL_ALREADY_IN_USE) {
						error(getString("global.message.duplicate.ID"));
					} else {
						error(getString("global.message.duplicate.username"));
					}
					updatePage();
				}
			}

			@Override
			public void onCancel() {
				showNoUser();
			}
		};
		setEditor(panel);
		updatePage();
	}

	protected void showNoUser() {
		setEditor(new WebMarkupContainer("editor").setOutputMarkupId(true));
		updatePage();
	}
	
	protected void setEditor(Component editor) {
		replace(editor);
	}

	// ----------------------------------------------------

	protected IModel<List<RBUser>> registeredUsersModel() {
		return new AbstractLoadableDetachableModel<List<RBUser>>() {
			@Override
			public List<RBUser> load() {
				final Collection<RBUser> users = authModule.getDomainManager().loadUsers(context.getDomain(), 0, 1000);
				return new ArrayList<RBUser>(users);
			}
		};
	}

	private void editUser(final IModel<RBUser> model) {
		setEditor(new UserDetailPanel("editor", model, new AllRolesModel()) {
			@Override
			public void onSave() {
				super.onSave();
				updatePage();
			}
		});
		updatePage();
	}

	private void updatePage() {
		RBAjaxTarget.add(get("editor"));
		RBAjaxTarget.add(get("userList"));
	}
	
	// ----------------------------------------------------
	
	private class AllRolesModel extends AbstractLoadableModel<List<String>> {

		@Override
		public List<String> load() {
			List<String> result = new ArrayList<String>();
			for (RBRole role : RBRole.values()) {
				result.add(role.name());
			}
			return result;
		}
	}
	
}
