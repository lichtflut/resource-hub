/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.settings;

import de.lichtflut.rb.core.eh.RBException;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

/**
 * <p>
 * This {@link Panel} allows a user to change his password.
 * </p><p>
 * Created: Jan 3, 2012
 * </p>
 * @author Ravi Knox
 */
@SuppressWarnings("rawtypes")
public class ChangePasswordPanel extends Panel {

	// Enforced RegEx for passwords
	private final String PASSWORD_PATTERN = "^(?=.*[a-z]).{4,16}$";
	
	private final IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);
	private final IModel<String> newPassword;
	private final IModel<String> currentPassword;
	private final IModel<String> confirmedPassword;
	
	@SpringBean
	private SecurityService securityService;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param model - a {@link IModel} wrapping a {@link RBUser}
	 */
	public ChangePasswordPanel(String id) {
		super(id);
		this.currentPassword = Model.of("");
		this.newPassword = Model.of("");
		this.confirmedPassword = Model.of("");
		Form form = new Form("form");
		
		populateForm(form);
		
		form.add(createSaveButton("save", form));
		form.add(createCancelButton("cancel", form));
		form.add(createEditButton("edit", form));
		this.add(form);
	}

	// ------------------------------------------------------

	/**
	 * Populate the form.
	 * @param form
	 */
	private void populateForm(Form form) {
		WebMarkupContainer container = new WebMarkupContainer("container");
		PasswordTextField currentPassField = new PasswordTextField("current", currentPassword);
		PasswordTextField newPassField = new PasswordTextField("newPassword", newPassword);
		PasswordTextField confirmPassField = new PasswordTextField("confirmPassword", confirmedPassword);
		
		newPassField.add(new PatternValidator(PASSWORD_PATTERN));
		form.add(new EqualPasswordInputValidator(newPassField, confirmPassField));
		
		container.add(currentPassField, newPassField, confirmPassField);
		container.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		form.add(new FeedbackPanel("feedback"));
		form.add(container);
	}

	/**
	 * @param string - wicket:id 
	 * @param form 
	 * @return a {@link AjaxButton}
	 */
	private AjaxButton createSaveButton(String id, Form form) {
		AjaxButton save = new RBStandardButton(id){
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form){
				CurrentUserModel user = new CurrentUserModel();
				try {
					setNewPassword(user.getObject(), currentPassword.getObject(), newPassword.getObject());
					mode.setObject(DisplayMode.VIEW);
				} catch (RBException e) {
					error(getString("error.invalid-password"));
				}
				RBAjaxTarget.add(form);
			}
			
			private void setNewPassword(RBUser user, String currentPassword, String newPassword) throws RBException {
				securityService.changePassword(user, currentPassword, newPassword);
				info(getString("info.password-changed"));
			}
		};
		save.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return save;
	}
	
	/**
	 * @param string
	 * @param form
	 * @return
	 */
	private RBCancelButton createCancelButton(String id, Form form) {
		RBCancelButton cancel = new RBCancelButton(id){
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				mode.setObject(DisplayMode.VIEW);
				RBAjaxTarget.add(form);
			}
		};
		cancel.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return cancel;
	}
	
	/**
	 * @param string - wicket:id 
	 * @param form 
	 * @return a {@link AjaxButton}
	 */
	private AjaxButton createEditButton(String id, Form form) {
		AjaxButton edit = new RBStandardButton(id){
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form){
				mode.setObject(DisplayMode.EDIT);
				RBAjaxTarget.add(form);
			}
		};
		edit.add(visibleIf(areEqual(mode, DisplayMode.VIEW)));
		return edit;
	}
}
