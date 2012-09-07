package de.lichtflut.rb.webck.components.rteditor;

import org.apache.wicket.markup.html.form.TextArea;
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
public class RichTextEditor extends TextArea {

    public RichTextEditor(String id) {
        super(id);

        add(new RichTextBehavior());
    }

    public RichTextEditor(String id, IModel<?> model) {
        super(id, model);

        add(new RichTextBehavior());
    }

}
