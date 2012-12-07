package de.lichtflut.rb.webck.components.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.fields.FieldLabelModel;
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

	@SpringBean
	private EntityManager entityManager;

	// ----------------------------------------------------

	public EmbeddedReferencePanel(final String id, final IModel<RBField> model) {
		super(id);

		add(new Label("label", new FieldLabelModel(model)));

		IModel<List<RBEntity>> entityListModel = new ReferencedEntitiesModel(model);
		add(createEntityRefs(entityListModel));

	}

	// ----------------------------------------------------

	/**
	 * Create a list with a column for each value of each field.
	 */
	protected Component createEntityRefs(final IModel<List<RBEntity>> entities) {
		final ListView<RBEntity> view = new ListView<RBEntity>("entities", entities) {
			@Override
			protected void populateItem(final ListItem<RBEntity> item) {
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

	private class ReferencedEntitiesModel extends DerivedDetachableModel<List<RBEntity>, RBField> {

		public ReferencedEntitiesModel(final IModel<RBField> model) {
			super(model);
		}

		@Override
		protected List<RBEntity> derive(final RBField field) {
			List<RBEntity> result = new ArrayList<RBEntity>(field.getSlots());
			for (Object obj: field.getValues()) {
				if (obj instanceof RBEntity) {
					result.add((RBEntity) obj);
				} else {
					throw new IllegalArgumentException("Expected an RBEntity but got: " + obj.getClass());
				}
			}
			if (result.isEmpty()) {
				// no values for this field yet - create and link a new one
				final ResourceID type = field.getConstraint().getTypeConstraint();
				RBEntity entity = entityManager.create(type);
				field.addValue(entity);
				result.add(entity);
			}
			return result;
		}
	}

}
