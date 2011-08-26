/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;

import de.lichtflut.rb.core.api.impl.NewRBEntityManagement;
import de.lichtflut.rb.core.mock.MockRBEntityManagement;
import de.lichtflut.rb.core.mock.MockResourceSchemaFactory;
import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
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

	private ListView<DropDownChoice<IRBEntity>> listView;
	private int identifier = 1;
	private IChoiceRenderer<IRBEntity> renderer;
	private List<DropDownChoice<IRBEntity>> ddcList;

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field - instance of {@link IRBField}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CKDropDownChoice(final String id,final IRBField field) {
		super(id);
		setOutputMarkupId(true);
		ddcList = new ArrayList<DropDownChoice<IRBEntity>>();
		final IModel<List<IRBEntity>> listModel = new ListModel(field.getFieldValues());
		final List<IRBEntity> allEntites = (List<IRBEntity>) new MockRBEntityManagement()
			.loadAllEntitiesForSchema(MockResourceSchemaFactory.createPersonSchema());
		add(createListView(listModel, allEntites));
		add(new AddValueAjaxButton("button", field){

			@Override
			public void addField(final AjaxRequestTarget target, final Form<?> form) {
				listView.getModelObject().add((DropDownChoice<IRBEntity>) new DropDownChoice<IRBEntity>("resourceList",
						new PropertyModel(listModel, "" + (identifier++)),
						allEntites, renderer).setOutputMarkupId(true));
				target.add(listView.getParent());
			}
		});
	}

	// ------------------------------------------------------------

	/**
	 * Creates a {@link ListView} containing {@link DropDownChoice}.
	 *
	 * @param listModel	- instance of {@link ListModel}
	 * @param allEntites - List of all {@link IRBEntity} available of the resource-type
	 * @return A ListView of {@link DropDownChoice}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ListView<DropDownChoice<IRBEntity>> createListView(
			final IModel<List<IRBEntity>> listModel, final List<IRBEntity> allEntites) {
//		Use this to get the ResourceTypeId for IRBManagement to loadAllRBentites(uri)
//		ResourceID uri = null;
//		for (Constraint c : field.getConstraints()) {
//			uri = c.getResourceTypeConstraint();
//		}
		renderer = new IChoiceRenderer<IRBEntity>() {
			@Override
			public Object getDisplayValue(final IRBEntity object) {
				return object.toString();
			}
			@Override
			public String getIdValue(final IRBEntity object, final int index) {
				return object.getQualifiedName().toString();
			}
		};

		ddcList.add(new DropDownChoice<IRBEntity>("resourceList",
				new PropertyModel(listModel, "" + (identifier++)), allEntites, renderer));
		listView = new ListView<DropDownChoice<IRBEntity>>("listView", ddcList) {
			@Override
			protected void populateItem(final ListItem<DropDownChoice<IRBEntity>> item) {
				item.add(item.getModelObject());
			}
		};
		listView.setReuseItems(true);
		listView.setOutputMarkupId(true);
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
	public NewRBEntityManagement getServiceProvider() {
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
