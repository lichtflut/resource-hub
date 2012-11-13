/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.identities;

import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

/**
 * <p>
 *  Panel for editing of a user.
 * </p>
 *
 * <p>
 * 	Created Dec 8, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class UserDetailPanel extends TypedPanel<RBUser> {
	
	@SpringBean
	private SecurityService securityService;
	
	@SpringBean 
	private ServiceContext context;
	
	private final IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);

	private final CurrentRolesModel currentRolesModel;
	
	private final IModel<List<String>> allRolesModel;

	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public UserDetailPanel(String id, IModel<RBUser> model, IModel<List<String>> availableRoles) {
		super(id, model);
		
		allRolesModel = availableRoles;
		
		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		
		form.add(new FeedbackPanel("feedback"));
		
		final TextField idField = new EmailTextField("id", new PropertyModel<String>(model, "email"));
		idField.add(ConditionalBehavior.enableIf(areEqual(mode, DisplayMode.EDIT)));
		idField.setRequired(true);
		form.add(idField);
		
		TextField usernameField = new TextField<String>("username",  new PropertyModel<String>(model, "username"));
		usernameField.add(ConditionalBehavior.enableIf(areEqual(mode, DisplayMode.EDIT)));
		form.add(usernameField);

		final Link resetPasswordLink = createResetPasswordLink();
		resetPasswordLink.add(ConditionalBehavior.enableIf(areEqual(mode, DisplayMode.EDIT)));
		form.add(resetPasswordLink);
		
		currentRolesModel = new CurrentRolesModel(model);
		
		final UserRolePanel userRolePanel = new UserRolePanel("rolePanel", model, currentRolesModel, allRolesModel);
		userRolePanel.add(ConditionalBehavior.enableIf(areEqual(mode, DisplayMode.EDIT)));
		form.add(userRolePanel);
		
		form.add(createSaveButton(form));
		form.add(createCancelButton(form));
		form.add(createEditButton(form));
		
		add(form);
		
		setOutputMarkupId(true);
	}

	// ----------------------------------------------------
	
	private Link createResetPasswordLink() {
		return new AjaxFallbackLink("resetPasswordLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				DialogHoster hoster = findParent(DialogHoster.class);
				final ConfirmationDialog confirmDialog = new ConfirmationDialog(hoster.getDialogID(),
						new ResourceModel("global.message.reset-password-confirmation")) {
					@Override
					public void onConfirm() {
						try {
							securityService.resetPasswordForUser(UserDetailPanel.this.getModelObject(), null);
						} catch (RBException e) {
							error(getString("error.send.email"));
						}
					}
				};
				
				hoster.openDialog(confirmDialog);
			}
		};
	}
	
	// ----------------------------------------------------
	
	public void onSave() {
		try {
			securityService.updateUser(getModelObject());
			securityService.setUserRoles(getModelObject(), context.getDomain(), currentRolesModel.getObject());
			mode.setObject(DisplayMode.VIEW);
		} catch (RBException e) {
			long code = e.getErrorCode();
			if (ErrorCodes.SECURITY_EMAIL_ALREADY_IN_USE == code) {
				error(getString("global.message.duplicate.email"));
			} else if (ErrorCodes.SECURITY_USERNAME_ALREADY_IN_USE == code) {
				error(getString("global.message.duplicate.username"));
			} else {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void onEdit() {
		mode.setObject(DisplayMode.EDIT);
	}
	
	public void onCancel() {
		mode.setObject(DisplayMode.VIEW);
	}
	
	// -- BUTTONS -----------------------------------------
	
	protected Button createSaveButton(final Form form) {
		final AjaxButton save = new RBStandardButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onSave();
				RBAjaxTarget.add(form);
			}
		};
		save.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return save;
	}
	
	protected Button createCancelButton(final Form form) {
		final AjaxButton cancel = new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onCancel();
				RBAjaxTarget.add(form);
			}
		};
		cancel.add(visibleIf(not(areEqual(mode, DisplayMode.VIEW))));
		return cancel;
	}
	
	protected Button createEditButton(final Form form) {
		final Button edit = new RBStandardButton("edit") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				onEdit();
				RBAjaxTarget.add(form);
			}
		};
		edit.add(visibleIf(areEqual(mode, DisplayMode.VIEW)));
		return edit;
	}
	
	// -- MODELS ------------------------------------------
	
	private class CurrentRolesModel extends DerivedDetachableModel<List<String>, RBUser> {

		public CurrentRolesModel(IModel<RBUser> userModel) {
			super(userModel);
		}
		
		@Override
		protected List<String> derive(RBUser user) {
			String domain = context.getDomain();
			return securityService.getUserRoles(user, domain);
		}
		
	}
	
}
	
