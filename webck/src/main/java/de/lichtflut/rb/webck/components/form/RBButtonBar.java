/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.form;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * <p>
 * This {@link Panel} provides Save, Cancel and Delete Buttons.
 * </p>
 * <p>
 * Visibility and Behavior can be configured as needed.
 * </p>
 * Created: Jun 6, 2012
 *
 * @author Ravi Knox
 */
public class RBButtonBar extends Panel{

	/**
	 * Constructor
	 */
	public RBButtonBar(final String id) {
		super(id);
		add(createDeleteButton());
		add(createCancelButton());
		add(createSubmitButton());
	}

	// ------------------------------------------------------

	protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

	}

	protected void onDelete(final AjaxRequestTarget target, final Form<?> form) {

	}

	protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {

	}

	public RBButtonBar setSubmitVisibility(final boolean visibility) {
		get("save").setVisibilityAllowed(visibility);
		return this;
	}

	public RBButtonBar setCancelVisibility(final boolean visibility) {
		get("cancel").setVisibilityAllowed(visibility);
		return this;
	}

	public RBButtonBar setDeleteVisibility(final boolean visibility) {
		get("delete").setVisibilityAllowed(visibility);
		return this;
	}

	// ------------------------------------------------------

	private Component createSubmitButton() {
		RBStandardButton submit= new RBStandardButton("save") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				RBButtonBar.this.onSubmit(target, form);
			}
		};
		//		submit.setVisible(setSubmitVisibility(true));
		return submit;
	}


	private Component createCancelButton() {
		RBCancelButton cancel = new RBCancelButton("cancel"){
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				onCancel(target, form);
			}
		};
		//		cancel.setVisible(setCancelVisibility());
		return cancel;
	}

	private Component createDeleteButton() {
		RBDefaultButton delete = new RBDefaultButton("delete") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				onDelete(target, form);
			}
		};
		//		delete.setVisible(setDeleteVisibility());
		return delete;
	}

}
