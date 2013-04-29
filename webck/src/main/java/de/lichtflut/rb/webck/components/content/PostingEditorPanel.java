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

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.webck.components.common.TypedPanel;
import de.lichtflut.rb.webck.components.rteditor.RichTextBehavior;
import de.lichtflut.rb.webck.components.rteditor.RichTextEditor;

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
public class PostingEditorPanel extends TypedPanel<ContentItem> {

	public PostingEditorPanel(final String id, final IModel<ContentItem> model) {
		super(id, model);

		setOutputMarkupId(true);

		final Form<?> form = new Form<Void>("form");
		form.add(new FeedbackPanel("feedback"));
		form.add(new RichTextEditor("editor", model, RichTextBehavior.Type.FULL_FEATURED) {
			@Override
			public void onSave() {
				PostingEditorPanel.this.onSave(model.getObject());
			}

			@Override
			public void onCancel() {
				PostingEditorPanel.this.onCancel(model.getObject());
			}
		});
		add(form);

	}

	// ----------------------------------------------------

	public void onSave(final ContentItem item) {
	}

	public void onCancel(final ContentItem item) {
	}
}
