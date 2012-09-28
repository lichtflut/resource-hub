package de.lichtflut.rb.webck.components.rteditor;

import de.lichtflut.rb.core.content.ContentItem;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  This component represents a rich text editor.
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
    public RichTextEditor(String id, IModel<ContentItem> model) {
        super(id, model);

        final TextField title = new TextField("title", titleModel(model));
        add(title);

        final TextArea<String> area = new TextArea<String>("editingArea", contentModel(model));
        area.add(new RichTextBehavior());
        add(area);

        add(new RBDefaultButton("save") {
            @Override
            protected void applyActions(AjaxRequestTarget target, Form<?> form) {
                onSave();
            }
        });

        add(new RBStandardButton("cancel") {
            @Override
            protected void applyActions(AjaxRequestTarget target, Form<?> form) {
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

    private IModel<String> contentModel(IModel<ContentItem> model) {
        return new DerivedDetachableModel<String, ContentItem>(model) {

            @Override
            protected String derive(ContentItem item) {
                return item.getContentAsString();
            }

            @Override
            public void setObject(String content) {
                getOriginal().setContent(content);
            }
        };
    }

    private IModel<String> titleModel(IModel<ContentItem> model) {
        return new DerivedDetachableModel<String, ContentItem>(model) {

            @Override
            protected String derive(ContentItem item) {
                return item.getTitle();
            }

            @Override
            public void setObject(String title) {
                getOriginal().setTitle(title);
            }
        };
    }

}
