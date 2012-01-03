/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.arastreju.sge.security.User;

/**
 * [TODO Insert description here.]
 * 
 * Created: Jan 3, 2012
 *
 * @author Ravi Knox
 */
public class ChangePasswordPanel extends Panel {

	private final String PASSWORD_PATTERN = "";
	private IModel<User> user;
	private IModel<Mode> mode = new Model<Mode>(Mode.VIEW);

	public enum Mode {
		EDIT, VIEW;
	}
	
	/**
	 * @param id
	 * @param model
	 */
	@SuppressWarnings("rawtypes")
	public ChangePasswordPanel(String id, Model<User> user) {
		super(id);
		this.user = user;
		add(new FeedbackPanel("feedback"));
		Form form = new Form("form");
		final PasswordTextField current = new PasswordTextField("currentPassword", Model.of(""));
		final PasswordTextField newPAssword = new PasswordTextField("newPassword", Model.of(""));
		final PasswordTextField confirm = new PasswordTextField("confirmPassword", Model.of(""));
		newPAssword.add(new PatternValidator(PASSWORD_PATTERN));
		form.add(current, newPAssword, confirm);
		form.add(new EqualPasswordInputValidator(newPAssword, confirm));
		form.add(createEditButton("edit", form));
		form.add(createSaveButton("save", form));
		this.add(form);
	}

	/**
	 * @param form 
	 * @param string 
	 * @return
	 */
	private AjaxFallbackButton createSaveButton(String id, Form form) {
		AjaxFallbackButton save = new AjaxFallbackButton(id, form) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				mode.setObject(Mode.VIEW);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		};
		save.add(visibleIf(areEqual(mode, Mode.EDIT)));
		return save;
	}

	/**
	 * @param form 
	 * @param string 
	 * @return
	 */
	private AjaxFallbackButton createEditButton(String id, Form form) {
		AjaxFallbackButton edit = new AjaxFallbackButton(id, form) {

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
