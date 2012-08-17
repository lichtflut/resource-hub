package de.lichtflut.rb.webck.components.entity;

import java.util.List;

import de.lichtflut.rb.webck.models.basic.CastingModel;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;
import de.lichtflut.rb.webck.models.fields.RBFieldsListModel;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedReferencePanel.class);

    // ----------------------------------------------------

    public EmbeddedReferencePanel(String id, RBFieldValueModel model, FieldEditorFactory factory) {
        super(id);

        IModel<RBEntity> entityModel = new CastingModel<RBEntity>(model);

        RBFieldsListModel fieldsListModel = new RBFieldsListModel(entityModel);

        add(createColumns(fieldsListModel, factory));
    }

    // ----------------------------------------------------

    /**
     * Create a list with a column for each value of each field.
     */
    protected Component createColumns(IModel<List<RBField>> listModel, final FieldEditorFactory factory) {
        final ListView<RBField> view = new ListView<RBField>("columns", listModel) {
            @Override
            protected void populateItem(ListItem<RBField> item) {
                RBField field = item.getModelObject();
                RBFieldValueModel valueModel = new RBFieldValueModel(field, 0);
                item.add(new Label("label", field.getLabel(getLocale())));
                item.add(factory.createField(valueModel, false));
                if (field.getVisualizationInfo().isFloating()) {
                    item.add(new AttributeAppender("class", Model.of("floating"), " "));
                }
            }
        };
        return view;
    }

}
