package de.lichtflut.rb.webck.components.entity;

import java.util.ArrayList;
import java.util.List;

import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
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

    public EmbeddedReferencePanel(String id, IModel<RBField> model) {
        super(id);

        IModel<List<RBEntity>> entityListModel = new ReferencedEntitiesModel(model);
        add(createEntityRefs(entityListModel));

    }

    // ----------------------------------------------------

    /**
     * Create a list with a column for each value of each field.
     */
    protected Component createEntityRefs(IModel<List<RBEntity>> entities) {
        final ListView<RBEntity> view = new ListView<RBEntity>("entities", entities) {
            @Override
            protected void populateItem(ListItem<RBEntity> item) {
                RBFieldsListModel fieldsListModel = new RBFieldsListModel(item.getModel());
                item.add(createRows(fieldsListModel));
            }
        };
        return view;
    }

    protected Component createRows(final IModel<List<RBField>> fieldListModel) {
        final ListView<RBField> view = new ListView<RBField>("rows", fieldListModel) {
            @Override
            protected void populateItem(final ListItem<RBField> item) {
                item.add(new EntityRowEditPanel("row", item.getModel()));
            }
        };
        return view;
    }

    private static class ReferencedEntitiesModel extends DerivedDetachableModel<List<RBEntity>, RBField> {

        public ReferencedEntitiesModel(IModel<RBField> model) {
            super(model);
        }

        @Override
        protected List<RBEntity> derive(RBField field) {
            List<RBEntity> result = new ArrayList<RBEntity>(field.getSlots());
            for (Object obj: field.getValues()) {
                if (obj instanceof RBEntity) {
                    result.add((RBEntity) obj);
                } else if (obj == null) {
                    result.add(null);
                } else {
                    throw new IllegalArgumentException("Expected an RBEntity but got: " + obj.getClass());
                }
            }
            if (result.isEmpty()) {
                result.add(null);
            }
            return result;
        }
    }
}
