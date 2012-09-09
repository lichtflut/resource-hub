package de.lichtflut.rb.webck.components.rteditor;

import de.lichtflut.rb.webck.components.form.RBStandardButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
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

    public RichTextEditor(String id, IModel<String> model) {
        super(id, model);

        final TextArea<String> area = new TextArea<String>("editingArea", model);
        area.add(new RichTextBehavior());
        add(area);

        add(new RBStandardButton("save"){
            @Override
            protected void applyActions(AjaxRequestTarget target, Form<?> form) {
                onSave();
            }
        });

        add(new RBStandardButton("cancel"){
            @Override
            protected void applyActions(AjaxRequestTarget target, Form<?> form) {
                onCancel();
            }
        });
    }

    // ----------------------------------------------------

    public void onSave() {
    }

    public void onCancel() {
    }

}
