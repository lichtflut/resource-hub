/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;

/**
 * <p>
 *  A standard confirmation dialog.
 * </p>
 *
 * <p>
 * 	Created Dec 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ConfirmationDialog extends AbstractRBDialog {

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public ConfirmationDialog(final String id, final IModel<String> messageModel) {
		super(id);
		
		final Form form = new Form("form");
		
		form.add(new Label("message", messageModel));
		
		form.add(new RBCancelButton("cancel"){
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				close(target);
				onCancel();
			}
		});
		form.add(new RBStandardButton("ok") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				close(target);
				onConfirm();
			}
		});
		add(form);
		
		setTitle(new ResourceModel("title.confirmation-dialog"));
		setModal(true);
		setWidth(400);
		setHeight(150);
	}
	
	// -- HOOKS -------------------------------------------
	
	public void onCancel() {
	}
	
	public void onConfirm() {
	}

}
