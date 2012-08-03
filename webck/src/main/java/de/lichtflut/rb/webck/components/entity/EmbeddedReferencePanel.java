package de.lichtflut.rb.webck.components.entity;

import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * <p>
 *  Panel for editing of an embedded resource reference.
 * </p>
 *
 * <p>
 *  Created 01.08.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedReferencePanel extends Panel {

    public EmbeddedReferencePanel(String id, IModel<RBFieldValueModel> model, FieldEditorFactory factory) {
        super(id);


    }


}
