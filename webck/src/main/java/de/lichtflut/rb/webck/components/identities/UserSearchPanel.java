/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.identities;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.components.fields.UserPickerField;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
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
public class UserSearchPanel extends Panel {
	
	private static final Logger logger = LoggerFactory.getLogger(UserSearchPanel.class);
	
	@SpringBean
	private AuthModule authModule;
	
	@SpringBean
	private ServiceContext serviceContext;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id The wicket ID.
	 */
	@SuppressWarnings("rawtypes")
	public UserSearchPanel(String id) {
		super(id);
		
		final IModel<QualifiedName> model = new Model<QualifiedName>();
		
		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));
		
		form.add(new UserPickerField("userpicker", model).setRequired(true));
		
		form.add(new RBDefaultButton("add") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				RBUser user = authModule.getUserManagement().findUser(model.getObject().toURI());
				logger.info("Selected user {} for import.", user.getName());
				userSelected(user);
			}
		});
		
		add(form);
	}
	
	// ----------------------------------------------------
	
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
	
	public void addUserToDomain(RBUser user) {
		String domain = serviceContext.getDomain();
		try {
			authModule.getUserManagement().grantAccessToDomain(user, domain);
		} catch (RBAuthException e) {
			logger.error("Failed to add user '" + user.getEmail() + "' to domain " + domain, e);
			error(getString("error.add-user-failed"));
		}
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.USER));
	}

}
