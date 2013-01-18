/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.dialogs.ConfirmationDialog;

/**
 * <p>
 *  Standard button
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBStandardButton extends AjaxButton {

	private IModel<String> confirmationMessage;

	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public RBStandardButton(final String id) {
		super(id);
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onError(final AjaxRequestTarget target, final Form<?> form) {
		RBAjaxTarget.add(form);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
		if (confirmationMessage != null) {
			requestConfirmation(form);
		} else {
			applyActions(target, form);
		}
	}

	/**
	 * Called when the form was successfully submitted and all preconditions were met.
	 * @param target The ajax request target.
	 * @param form The submitted form.
	 */
	protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
	}

	// ----------------------------------------------------

	public void needsConfirmation(final IModel<String> message) {
		this.confirmationMessage = message;
	}

	// ----------------------------------------------------

	private void requestConfirmation(final Form<?> form) {
		final DialogHoster hoster = findParent(DialogHoster.class);
		hoster.openDialog(new ConfirmationDialog(hoster.getDialogID(), confirmationMessage) {
			@Override
			public void onConfirm() {
				applyActions(RequestCycle.get().find(AjaxRequestTarget.class), form);
			}
		});
	}

}
