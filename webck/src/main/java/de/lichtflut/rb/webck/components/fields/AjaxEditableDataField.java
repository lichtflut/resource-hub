/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

/**
 * <p>
 * This component displays the data's name and opens an {@link FileUploadField} when clicked.
 * </p>
 * Created: Sep 5, 2012
 *
 * @author Ravi Knox
 */
public class AjaxEditableDataField<T> extends AjaxEditableLabel<T> {

	/**
	 * @param id
	 * @param model
	 */
	public AjaxEditableDataField(final String id, final IModel<T> model) {
		super(id, model);
		setOutputMarkupId(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected FormComponent newEditor(final MarkupContainer parent, final String componentId, final IModel<T> model) {
		FormComponentPanel container = new FormComponentPanel("container", model){

		};
		container.add(super.newEditor(parent, componentId, model));
		container.setOutputMarkupId(true);
		container.setVisible(false);
		container.add(new EditorAjaxBehavior());
		return container;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected WebMarkupContainer newLabel(final MarkupContainer parent, final String componentId,
			final IModel<T> model)
	{
		WebMarkupContainer container = new WebMarkupContainer("container");
		Label label = new Label(componentId, model)
		{
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void onComponentTagBody(final MarkupStream markupStream,
					final ComponentTag openTag)
			{
				String displayValue = getDefaultModelObjectAsString();

				if (Strings.isEmpty(displayValue))
				{
					replaceComponentTagBody(markupStream, openTag, defaultNullLabel());
				}
				else
				{
					replaceComponentTagBody(markupStream, openTag, displayValue);
				}
			}
		};
		label.setOutputMarkupId(true);
		container.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
		container.add(new Label("additionalInfo", Model.of(" (click to edit)")));
		container.add(label);
		return container;
	}
}
