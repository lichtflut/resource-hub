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

import de.lichtflut.rb.webck.components.perceptions.CreatePerceptionsWizardPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.ResourceModel;


/**
 * <p>
 *  Wizzard to create multiple Perceptions at once
 * </p>
 *
 * Created: Jan 9, 2013
 *
 * @author Ravi Knox
 */
public class CreatePerceptionsWizardDialog extends RBDialog {

	/**
	 * Constructor.
	 * @param id Component id
	 */
	public CreatePerceptionsWizardDialog(final String id) {
		super(id);
		add(new CreatePerceptionsWizardPanel("wizard"){
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				super.onSubmit(target, form);
				CreatePerceptionsWizardDialog.this.onUpdate(target, form);
			}

			@Override
			protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
				super.onCancel(target, form);
				CreatePerceptionsWizardDialog.this.onUpdate(target, form);
			}
		});

		setTitle(new ResourceModel("dialog.title"));
	}

	// ------------------------------------------------------


	protected void onUpdate(final AjaxRequestTarget target, final Form<?> form) {
	}

}
