package de.lichtflut.rb.webck.components.rteditor;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

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
