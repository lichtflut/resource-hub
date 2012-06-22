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

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
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
				final ActionLink deleteLink = new ActionLink("delete", new ResourceModel("link.delete")) {
					@Override
					public void onClick(AjaxRequestTarget target) {
						deleteUser(user);
					}
				};
				deleteLink.setLinkCssClass("action-delete");
				deleteLink.needsConfirmation(new ResourceModel("message.delete-confirmation"));
				item.add(deleteLink);
			}
		};
		
		add(usersView);
	}

	// ----------------------------------------------------

	public abstract void onUserSelected(RBUser user);
	
	// ----------------------------------------------------
	
	private String getLastLogin(RBUser user) {
		if (user != null && user.getLastLogin() != null) {
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(user.getLastLogin());
		}
		return "-";
	}
	
	private void deleteUser(final RBUser user) {
		securityService.deleteUser(user);
		RBAjaxTarget.add(this);
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.USER));
	}

}
