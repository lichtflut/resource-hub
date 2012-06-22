/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.identities;

import java.text.DateFormat;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.listview.ActionLink;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 *  Panel for listing of users.
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public abstract class UserListPanel extends Panel {
	
	@SpringBean
	private SecurityService securityService;
	
	@SpringBean
	private ServiceContext serviceContext;
	
	@SpringBean
	private AuthModule authModule;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The wicket ID.
	 * @param listModel The list model of the users to display.
	 */
	public UserListPanel(String id, IModel<List<RBUser>> listModel) {
		super(id);
		
		setOutputMarkupId(true);
		
		final ListView<RBUser> usersView = new ListView<RBUser>("list", listModel) {
			@Override
			protected void populateItem(final ListItem<RBUser> item) {
				final RBUser user = item.getModelObject();
				item.add(new Label("id", user.getName()));
				item.add(new Label("lastLogin", getLastLogin(user)));
				item.add(new Label("origin", user.getDomesticDomain()));
				item.add(new AjaxLink("edit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						onUserSelected(user);
					}
				});
				item.add(createRemoveLink(user));
			}
		};
		
		add(usersView);
	}

	// ----------------------------------------------------

	public abstract void onUserSelected(RBUser user);
	
	// ----------------------------------------------------
	
	protected ActionLink createRemoveLink(final RBUser user) {
		final ActionLink deleteLink = new ActionLink("delete", new ResourceModel("link.delete")) {
			@Override
			public void onClick(AjaxRequestTarget target) {
				if (isDomestic(user)) {
					deleteUser(user);
				} else {
					removeUserFromDomain(user);
				}
			}
		};
		deleteLink.setLinkCssClass("action-delete");
		if (isDomestic(user)) {
			deleteLink.needsConfirmation(new ResourceModel("message.delete-confirmation"));	
		} else {
			deleteLink.needsConfirmation(new ResourceModel("message.unassign-confirmation"));
		}
		return deleteLink;
	}
	
	private String getLastLogin(RBUser user) {
		if (user != null && user.getLastLogin() != null) {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(user.getLastLogin());
		}
		return "-";
	}
	
	private void deleteUser(RBUser user) {
		securityService.deleteUser(user);
		RBAjaxTarget.add(this);
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.USER));
	}
	
	private void removeUserFromDomain(RBUser user) {
		RBDomain domain = authModule.getDomainManager().findDomain(serviceContext.getDomain());
		try {
			authModule.getUserManagement().revokeAccessToDomain(user, domain);
		} catch (RBAuthException e) {
			throw new RuntimeException(e);
		}
		RBAjaxTarget.add(this);
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.USER));
	}
	
	private boolean isDomestic(RBUser user) {
		return Infra.equals(serviceContext.getDomain(), user.getDomesticDomain());
	}

}
