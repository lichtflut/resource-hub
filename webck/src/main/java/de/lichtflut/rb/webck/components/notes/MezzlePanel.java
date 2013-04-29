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
package de.lichtflut.rb.webck.components.notes;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.models.HTMLSafeModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import java.text.DateFormat;

/**
 * <p>
 *  Panel for display and editing of a 'mezzle' a 'memo-frazzle'.
 * </p>
 *
 * <p>
 * 	Created Jan 3, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class MezzlePanel extends TypedPanel<ContentItem> {

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model containing the mezzle node.
	 */
	@SuppressWarnings("rawtypes")
	public MezzlePanel(final String id, final IModel<ContentItem> model) {
		super(id, model);

		add(new Label("content", toHtmlSafeContentModel(model)).setEscapeModelStrings(false));

		add(new Label("creator", new DerivedModel<String, ContentItem>(model) {
			@Override
			protected String derive(final ContentItem contentItem) {
				return contentItem.getCreatorName();
			}
		}));

		add(new Label("created", new DerivedModel<String, ContentItem>(model) {
            @Override
            protected String derive(final ContentItem contentItem) {
				return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, getLocale()).format(
                        contentItem.getCreateDate());
			}
		}));

		add(new AjaxLink("edit") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				edit(model);
			}
		});

		add(new AjaxLink("delete") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				delete(model);
			}
		});
	}

	// ----------------------------------------------------

	private IModel<String> toHtmlSafeContentModel(final IModel<ContentItem> model) {
		return new HTMLSafeModel(new DerivedModel<String, ContentItem>(model) {
			@Override
			protected String derive(final ContentItem mezzle) {
				return mezzle.getContentAsString();
			}
		});
	}


	/**
	 * Hook for editing of notes.
	 * @param mezzle The node representing the mezzle.
	 */
	public abstract void edit(final IModel<ContentItem> mezzle);

	/**
	 * Hook for deleting of notes.
	 * @param mezzle The node representing the mezzle.
	 */
	public abstract void delete(final IModel<ContentItem> mezzle);

}
