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
