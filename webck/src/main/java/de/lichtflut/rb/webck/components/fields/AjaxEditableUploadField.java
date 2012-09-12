/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.webck.models.fields.FileUploadModel;

/**
 * <p>
 * This component displays the data's name and opens an {@link FileUploadField} when clicked.
 * </p>
 * Created: Sep 5, 2012
 *
 * @author Ravi Knox
 */
public class AjaxEditableUploadField extends AjaxEditableLabel<Object>{

	/**
	 * @param id - wicket:id
	 * @param model - {@link FileUploadModel}.
	 */
	public AjaxEditableUploadField(final String id, final IModel<Object> model) {
		super(id, model);
		setOutputMarkupId(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected FormComponent newEditor(final MarkupContainer parent, final String componentId, final IModel model) {
		final FileUploadField uploadField = new FileUploadField(componentId, model);

		uploadField.setOutputMarkupId(true);
		uploadField.setVisible(false);
		uploadField.add(new AjaxFormSubmitBehavior("onchange") {

			@Override
			protected void onSubmit(final AjaxRequestTarget target) {
				getEditor().setVisible(false);
				getLabel().setVisible(true);
				target.add(getEditor());
				target.appendJavaScript("window.status='';");
			}

			@Override
			protected void onError(final AjaxRequestTarget target) {
				onSubmit(target);

			}
		});
		return uploadField;
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
		container.add(new Label("additionalInfo", Model.of(" (click to edit)")));

		container.setOutputMarkupId(true);
		container.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
		return container;
	}
}
