/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.organizer;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.arastreju.sge.security.User;

/**
 * [TODO Insert description here.]
 * 
 * Created: Jan 3, 2012
 *
 * @author Ravi Knox
 */
@SuppressWarnings("rawtypes")
public class ChangePasswordPanel extends Panel {

	private IModel<User> user;
	private IModel<Mode> mode = new Model<Mode>(Mode.VIEW);
	private WebMarkupContainer container;

	public enum Mode {
		EDIT, VIEW;
	}
	
	/**
	 * @param id
	 * @param model
	 */
	public ChangePasswordPanel(String id, Model<User> user) {
		super(id);
		this.user = user;
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		Form form = new Form("form");
		createContainer(form);
		form.add(createSaveButton("save", form));
		form.add(createEditButton("edit", form));
		this.add(form);
	}

	/**
	 * @param string
	 * @param form
	 * @return
	 */
	private void createContainer(Form form) {
		
		if(mode.getObject().equals(Mode.VIEW)){
			container.removeAll();
			container.add(new Label("password", Model.of("Password")));
			
		}else{
			container.removeAll();
			container.add(new PasswordPanel("password", user));
		}
		form.add(container);
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
				createContainer(form);
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
	 * @param form 
	 * @param string 
	 * @return
	 */
	private AjaxFallbackButton createEditButton(String id, Form form) {
		AjaxFallbackButton edit = new AjaxFallbackButton(id, form) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				mode.setObject(Mode.EDIT);
				createContainer(form);
				target.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		};
		edit.add(visibleIf(areEqual(mode, Mode.VIEW)));
		return edit;
	}

	private class PasswordPanel extends Panel{

		final String PASSWORD_PATTERN = "";
		
		/**
		 * @param id - wicket:id
		 * @param model
		 */
		public PasswordPanel(String id, IModel<?> model) {
			super(id, model);
			Form form = new Form("changePasswordForm");
			PasswordTextField current = new PasswordTextField("current");
			PasswordTextField newPassword = new PasswordTextField("newPassword");
			PasswordTextField confirmPassword = new PasswordTextField("confirmPassword");
			form.add(current, newPassword, confirmPassword);
			form.add(new EqualPasswordInputValidator(newPassword, confirmPassword));
			form.add(current);
			this.add(form);
		}
		
	}
}
