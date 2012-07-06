/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.admin.identitymanagment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;

import de.lichtflut.rb.application.admin.AdminBasePage;
import de.lichtflut.rb.application.common.RBPermission;
import de.lichtflut.rb.application.common.RBRole;
import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.messaging.EmailConfiguration;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.dialogs.InformationExportDialog;
import de.lichtflut.rb.webck.components.identities.AddUserToDomainPanel;
import de.lichtflut.rb.webck.components.identities.UserCreationPanel;
import de.lichtflut.rb.webck.components.identities.UserDetailPanel;
import de.lichtflut.rb.webck.components.identities.UserListPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;

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
	
	@SpringBean 
	private ModelingConversation conversation;
	
	@SpringBean
	private EmailConfiguration emailConf;
	
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
				openDialog(new InformationExportDialog(getDialogID(), createExportModel()));
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
					RBUser registered = securityService.createUser(emailID, username, password,
							emailConf, getLocale());
					securityService.setUserRoles(registered, context.getDomain(), DEFAULT_ROLES);
					editUser(Model.of(registered));
				} catch (RBException e) {
					if (e.getErrorCode() == ErrorCodes.SECURITYSERVICE_EMAIL_ALREADY_IN_USE) {
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

	private IModel<SemanticGraph> createExportModel() {
		return new AbstractReadOnlyModel<SemanticGraph>() {
			@Override
			public SemanticGraph getObject() {
				final Query query = conversation.createQuery();
				query.addField(RDF.TYPE, Aras.USER);
				
				final SemanticGraph graph = new DefaultSemanticGraph();
				for (ResourceNode user : query.getResult()) {
					graph.addStatements(user.getAssociations());
				}
				return graph;
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
