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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.security.User;

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

	private IModel<String> newPassword;
	private IModel<Mode> mode = new Model<Mode>(Mode.VIEW);
	private IModel<String> mirroredPassword;

	private enum Mode {
		EDIT, VIEW;
	}
	
	// ------------------------------------------------------
	
	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param model - a {@link IModel} wrapping a {@link User}
	 */
	public ChangePasswordPanel(String id) {
		super(id);
		Form form = new Form("form");
		
		populateForm(form);
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
		PasswordTextField current = new PasswordTextField("current");
		PasswordTextField newPassword = new PasswordTextField("newPassword");
		PasswordTextField confirmPassword = new PasswordTextField("confirmPassword");
		container.add(current, newPassword, confirmPassword);
		container.add(new EqualPasswordInputValidator(newPassword, confirmPassword));
		container.add(visibleIf(areEqual(mode, Mode.EDIT)));
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
				// TODO: implement
				CurrentUserModel user = new CurrentUserModel();
				if(verify(user, mirroredPassword)){
					setNewPassword(user, newPassword);
				}
				mode.setObject(Mode.VIEW);
				target.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		};
		save.add(visibleIf(areEqual(mode, Mode.EDIT)));
		return save;
	}

	/**
	 * Verifies the current password with the typed one.
	 * @param user
	 * @param newPassword
	 * @return
	 */
	private boolean verify(IModel<User> user, IModel<String> mirroredPassword) {
		// TODO Auto-generated method stub
		return false;
	}


	private void setNewPassword(IModel<User> user, IModel<String> newPassword) {
		// TODO Auto-generated method stub
		
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
				mode.setObject(Mode.EDIT);
				target.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		};
		edit.add(visibleIf(areEqual(mode, Mode.VIEW)));
		return edit;
	}
}
