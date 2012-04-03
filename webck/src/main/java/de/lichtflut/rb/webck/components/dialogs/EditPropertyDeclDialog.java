/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.webck.components.typesystem.properties.EditPropertyDeclPanel;

/**
 * <p>
 *  Dialog for Editing {@link PropertyDeclaration}s.
 * </p>
 *
 * <p>
 * 	Created Mar 1, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class EditPropertyDeclDialog extends AbstractRBDialog {

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param decls
	 */
	public EditPropertyDeclDialog(String id, IModel<List<PropertyDeclaration>> decls) {
		super(id);
		add(new EditPropertyDeclPanel("panel", decls){
			/**
			 * Execute on onSubmit.
			 * @param form 
			 * @param target 
			 */
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				EditPropertyDeclDialog.this.onSubmit(target, form);
			}

			/**
			 * Execute on onCancel.
			 * @param form 
			 * @param target 
			 */
			protected void onCancel(AjaxRequestTarget target, Form<?> form) {
				EditPropertyDeclDialog.this.onCancel(target, form);
			}
		});
	}

	// ------------------------------------------------------

	/**
	 * Execute on onSubmit.
	 * @param form 
	 * @param target 
	 */
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	}

	/**
	 * Execute on onCancel.
	 * @param form 
	 * @param target 
	 */
	protected void onCancel(AjaxRequestTarget target, Form<?> form) {
	}
}
