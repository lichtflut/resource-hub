/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import de.lichtflut.rb.core.mock.MockRBEntityManagement;
import de.lichtflut.rb.core.mock.MockResourceSchemaFactory;
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.components.CKComponent;

/**
 * TODO: Replace IRBManagementService-Mock
 * This field displays a List of {@link IRBEntity} as a {@link DropDownChoice}.
 *
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class CKDropDownChoice extends CKComponent {

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field - instance of {@link IRBField}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CKDropDownChoice(final String id,final IRBField field) {
		super(id);
		IModel<List<IRBEntity>> listModel = new ListModel(field.getFieldValues());
		List<IRBEntity> allEntites = (List<IRBEntity>) new MockRBEntityManagement()
			.loadAllEntitiesForSchema(MockResourceSchemaFactory.createPersonSchema());
//		if (listModel.getObject().size() == 0) {
//			// Display at least one textfield.
//			listModel.getObject().add(false);
//		}
		add(createListView(listModel, allEntites));
	}

	// ------------------------------------------------------------

	/**
	 * Creates a {@link ListView} containing {@link DropDownChoice}.
	 *
	 * @param listModel	- instance of {@link ListModel}
	 * @param allEntites - List of all {@link IRBEntity} available of the resource-type
	 * @return A ListView of {@link DropDownChoice}
	 */
	private ListView<IRBEntity> createListView(
			final IModel<List<IRBEntity>> listModel, final List<IRBEntity> allEntites) {
//		Use this to get the ResourceTypeId for IRBManagement to loadAllRBentites(uri)
//		ResourceID uri = null;
//		for (Constraint c : field.getConstraints()) {
//			uri = c.getResourceTypeConstraint();
//		}

		final IChoiceRenderer<IRBEntity> renderer = new IChoiceRenderer<IRBEntity>() {
			@Override
			public Object getDisplayValue(final IRBEntity object) {
				return object.toString();
			}
			@Override
			public String getIdValue(final IRBEntity object, final int index) {
				return object.getQualifiedName().toString();
			}
		};

		final ListView<IRBEntity> listView = new ListView<IRBEntity>("listView",
				listModel) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			protected void populateItem(final ListItem<IRBEntity> item) {
				DropDownChoice<IRBEntity> ddc = new DropDownChoice<IRBEntity>("resourceList",
						new PropertyModel(listModel, "" + item.getId()), allEntites, renderer);
				item.add(ddc);
			}
		};
		listView.setReuseItems(true);
		setOutputMarkupId(true);
		return listView;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

}
