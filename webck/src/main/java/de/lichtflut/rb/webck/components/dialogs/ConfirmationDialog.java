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

import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

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
public class ConfirmationDialog extends RBDialog {

	/**
     * Constructor.
	 * @param id Whe wicket ID.
     * @param messageModel The model providing the display message.
	 */
	public ConfirmationDialog(final String id, final IModel<String> messageModel) {
		super(id);

		final Form<?> form = new Form<Void>("form");

		form.add(new Label("message", messageModel));

		form.add(new RBCancelButton("cancel"){
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				close();
				onCancel();
			}
		});
		form.add(new RBStandardButton("ok") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				close();
				onConfirm();
			}
		});
		add(form);

		setTitle(new ResourceModel("title.confirmation-dialog"));
		setModal(true);
	}

	// -- HOOKS -------------------------------------------

	public void onCancel() {
	}

	public void onConfirm() {
	}

}
