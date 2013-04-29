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
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.webck.models.fields.FileUploadModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import de.lichtflut.rb.webck.validator.FileSizeValidator;

/**
 * <p>
 * This component displays the data's name and opens an {@link FileUploadField} when clicked.<br>
 * Works with {@link RBFieldValueModel} by default.
 * </p>
 * Created: Sep 5, 2012
 *
 * @author Ravi Knox
 */
// TODO add wicket:for compatibility with ILabelProvider
public class AjaxEditableUploadField extends AjaxEditableLabel<Object> {

	private final long maximum;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param model - {@link FileUploadModel}.
	 */
	public AjaxEditableUploadField(final String id, final IModel<Object> model) {
		this(id, model, Long.MAX_VALUE);

	}

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param model - {@link FileUploadModel}.
	 * @param maximum - maximum filesize in byte
	 */
	public AjaxEditableUploadField(final String id, final IModel<Object> model, final long maximum) {
		super(id, model);
		this.maximum = maximum;
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected FormComponent newEditor(final MarkupContainer parent, final String componentId, final IModel model) {
		final FileUploadField uploadField = new FileUploadField(componentId, model);
		uploadField.setOutputMarkupId(true);
		uploadField.add(new FileSizeValidator(maximum));

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackpanel");
		feedbackPanel.setOutputMarkupId(true);

		Form<?> form = createForm(uploadField, feedbackPanel);

		final FormComponent<FileUploadField> formComponent = new FormComponent<FileUploadField>("formComponent", model) {};
		formComponent.setOutputMarkupId(true);
		formComponent.add(form);
		formComponent.setVisible(false);
		return formComponent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected WebMarkupContainer newLabel(final MarkupContainer parent, final String componentId,
			final IModel<Object> model)
	{
		WebMarkupContainer container = new WebMarkupContainer("container");
		Label label = new Label(componentId, model);

		container.add(label);
		container.add(new Label("additionalInfo", getAdditionalInfo()));

		container.setOutputMarkupId(true);
		container.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
		return container;
	}

	/**
	 * @return the String that will be appended to the Label
	 */
	protected Model<String> getAdditionalInfo() {
		return Model.of(" (click to edit)");
	}

	// ------------------------------------------------------

	private Form<?> createForm(final FileUploadField uploadField, final FeedbackPanel feedbackPanel) {
		Form<?> form = new Form<Void>("uploadForm");
		form.add(feedbackPanel);
		form.add(uploadField);

		form.add(new AjaxFormValidatingBehavior(form, "onChange"){

			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				getLabel().modelChanged();
				getEditor().setVisible(false);
				getLabel().setVisible(true);
				target.add(AjaxEditableUploadField.this);
				target.appendJavaScript("window.status='';");
			}

			@Override
			protected void onError(final AjaxRequestTarget target) {
				target.add(feedbackPanel);
			}

		});
		return form;
	}

}
