package de.lichtflut.rb.webck.components.rteditor;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  This component represents a rich text editor. This editor does not bring it's own form.
 *  So you have to place it in a form yourself.
 * </p>

 * <p>
 *  Created 06.09.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class RichTextEditor extends Panel {

	/**
	 * Create a new rich text editor.
	 * @param id The component ID.
	 * @param model The content model.
	 */
	public RichTextEditor(final String id, final IModel<ContentItem> model) {
		this(id, model, RichTextBehavior.Type.STANDARD);
	}

	public RichTextEditor(final String id, final IModel<ContentItem> model, final RichTextBehavior.Type type) {
		super(id, model);

		add(createTitleField("title", titleModel(model)));

		final TextArea<String> area = new TextArea<String>("editingArea", contentModel(model));
		area.add(new RichTextBehavior(type));
		add(area);

		add(new RBDefaultButton("save") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				onSave();
			}
		});

		add(new RBStandardButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				onCancel();
			}
		});
	}

	// ----------------------------------------------------

	/**
	 * Hook for save action.
	 */
	public void onSave() {
	}

	/**
	 * Hook for cancel action.
	 */
	public void onCancel() {
	}

	// ----------------------------------------------------

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Component createTitleField(final String componentID, final IModel<String> titleModel) {
		return new TextField(componentID, titleModel);
	}

	// ----------------------------------------------------

	private IModel<String> contentModel(final IModel<ContentItem> model) {
		return new DerivedDetachableModel<String, ContentItem>(model) {

			@Override
			protected String derive(final ContentItem item) {
				return item.getContentAsString();
			}

			@Override
			public void setObject(final String content) {
				getOriginal().setContent(content);
			}
		};
	}

	private IModel<String> titleModel(final IModel<ContentItem> model) {
		return new DerivedDetachableModel<String, ContentItem>(model) {

			@Override
			protected String derive(final ContentItem item) {
				return item.getTitle();
			}

			@Override
			public void setObject(final String title) {
				getOriginal().setTitle(title);
			}
		};
	}

}
