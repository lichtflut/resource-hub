/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.components.dialogs;


import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.webck.components.typesystem.PropertyRow;
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
	 * @param decl
	 */
	public EditPropertyDeclDialog(final String id, final IModel<PropertyRow> decl, final IModel<ResourceSchema> schema) {
		super(id);
		add(new EditPropertyDeclPanel("panel", decl, schema){
			/**
			 * Execute on onSubmit.
			 * @param form
			 * @param target
			 */
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				super.onSubmit(target, form);
				close(target);
				EditPropertyDeclDialog.this.onSubmit(target, form);
			}

			/**
			 * Execute on onCancel.
			 * @param form
			 * @param target
			 */
			@Override
			protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
				close(target);
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
	protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
	}

	/**
	 * Execute on onCancel.
	 * @param form
	 * @param target
	 */
	protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
	}
}
