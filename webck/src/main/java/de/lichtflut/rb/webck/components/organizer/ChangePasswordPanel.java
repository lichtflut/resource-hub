/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

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
import org.arastreju.sge.security.User;

import de.lichtflut.infra.security.Crypt;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.models.CurrentUserModel;

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

	private final String PATTERN = "^(?=.*[a-z]).{4,16}$";
	
	private IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);
	private IModel<String> newPassword;
	private IModel<String> currentPassword;
	private IModel<String> confirmedPassword;
	
	@SpringBean
	ServiceProvider provider;
	
	// ------------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param model - a {@link IModel} wrapping a {@link User}
	 */
	public ChangePasswordPanel(String id) {
		super(id);
		this.currentPassword = Model.of("");
		this.newPassword = Model.of("");
		this.confirmedPassword = Model.of("");
		Form form = new Form("form");
		
		populateForm(form);
		
		form.add(new FeedbackPanel("feedback"));
		form.add(createSaveButton("save", form));
		form.add(createEditButton("edit", form));
		this.add(form);
	}

	/**
	 * Populate the form.
	 * @param form
	 */
	private void populateForm(Form form) {
		WebMarkupContainer container = new WebMarkupContainer("container");
		PasswordTextField currentPassField = new PasswordTextField("current", currentPassword);
		PasswordTextField newPassField = new PasswordTextField("newPassword", newPassword);
		PasswordTextField confirmPassField = new PasswordTextField("confirmPassword", confirmedPassword);
		newPassField.add(new PatternValidator(PATTERN));
		container.add(currentPassField, newPassField, confirmPassField);
		container.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		form.add(new EqualPasswordInputValidator(newPassField, confirmPassField));
		form.add(container);
	}

	/**
	 * @param string - wicket:id 
	 * @param form 
	 * @return a {@link AjaxButton}
	 */
	private AjaxButton createSaveButton(String id, Form form) {
		AjaxButton save = new AjaxButton(id, form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				CurrentUserModel user = new CurrentUserModel();
				setNewPassword(user.getObject(), currentPassword.getObject(), newPassword.getObject());
				mode.setObject(DisplayMode.VIEW);
				target.add(form);
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		};
		save.add(visibleIf(areEqual(mode, DisplayMode.EDIT)));
		return save;
	}

	private void setNewPassword(User user, String currentPassword, String newPassword) {
		String currentPwd = Crypt.md5Hex(currentPassword);
		String newPwd = Crypt.md5Hex(newPassword);
		provider.getSecurityService().setNewPassword(user, currentPwd, newPwd);
	}

	/**
	 * @param string - wicket:id 
	 * @param form 
	 * @return a {@link AjaxButton}
	 */
	private AjaxButton createEditButton(String id, Form form) {
		AjaxButton edit = new AjaxButton(id, form) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				mode.setObject(DisplayMode.EDIT);
				target.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		};
		edit.add(visibleIf(areEqual(mode, DisplayMode.VIEW)));
		return edit;
	}
}
