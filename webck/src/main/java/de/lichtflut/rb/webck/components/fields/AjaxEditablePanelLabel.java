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
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Mar 5, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class AjaxEditablePanelLabel<T> extends AjaxEditableLabel<T> {

	/**
	 * @param id
	 * @param model
	 */
	public AjaxEditablePanelLabel(String id, IModel<T> model) {
		super(id, model);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected FormComponent<T> newEditor(final MarkupContainer parent, final String componentId,
		final IModel<T> model)
	{
		@SuppressWarnings({ "rawtypes" })
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
	protected WebComponent newLabel(final MarkupContainer parent, final String componentId,
		final IModel<T> model)
	{
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
		label.add(new LabelAjaxBehavior(getLabelAjaxEvent()));
		return label;
	}

}
