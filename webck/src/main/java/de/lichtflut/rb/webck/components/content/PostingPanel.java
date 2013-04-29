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
package de.lichtflut.rb.webck.components.content;

import java.text.DateFormat;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  This panel display a single posting.
 * </p>
 * <p>
 *  A posting can be one of
 *  <ul>
 *      <li>News</li>
 *      <li>Blog post</li>
 *      <li>Forum entry</li>
 *      <li>Article</li>
 *  </ul>
 *
 * </p>
 *
 * <p>
 *  Created 04.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PostingPanel extends TypedPanel<ContentItem> {

	public PostingPanel(final String id, final IModel<ContentItem> model) {
		super(id, model);

		setOutputMarkupId(true);

		add(new Label("title", getTitleModel()));
		add(new Label("author", getAuthorModel()));
		add(new Label("created", getCreationModel()));
		add(new Label("content", getContentDisplayModel(model)).setEscapeModelStrings(false));

		add(new AjaxLink<Void>("edit") {
			@Override
			public void onClick(final AjaxRequestTarget target) {
				onEdit(model.getObject());
			}
		});
	}

	// -- CALLBACKS----------------------------------------

	public void onEdit(final ContentItem item) {
	}

	// ----------------------------------------------------

	private ContentItem getContentItem() {
		return getModelObject();
	}


	protected IModel<String> getAuthorModel() {
		return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
			@Override
			protected String derive(final ContentItem item) {
				return item.getCreatorName();
			}
		};
	}

	protected IModel<String> getCreationModel() {
		return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
			@Override
			protected String derive(final ContentItem item) {
				Date date = item.getCreateDate();
				if (date != null) {
					return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, getLocale())
							.format(date);
				} else {
					return "";
				}
			}
		};
	}

	protected IModel<String> getTitleModel() {
		return new DerivedDetachableModel<String, ContentItem>(getContentItem()) {
			@Override
			protected String derive(final ContentItem item) {
				return item.getTitle();
			}
		};
	}

	private IModel<String> getContentDisplayModel(final IModel<ContentItem> model) {
		return new DerivedDetachableModel<String, ContentItem>(model) {
			@Override
			protected String derive(final ContentItem item) {
				return item.getContentAsString();
			}
		};
	}

}
