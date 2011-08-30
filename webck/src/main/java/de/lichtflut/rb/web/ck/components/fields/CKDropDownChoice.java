/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.fields;

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;

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
public abstract class CKDropDownChoice extends CKComponent {

	private RepeatingView view;
	private ListView<IRBEntity> listView;
	private int identifier = 1;
	private IChoiceRenderer<IRBEntity> renderer;
	private List<DropDownChoice<IRBEntity>> ddcList;
	private IRBField field;

	/**
	 * Constructor.
	 * @param id - wicket:id
	 * @param field - instance of {@link IRBField}
	 */
	public CKDropDownChoice(final String id,final IRBField field) {
		super(id);
		this.field = field;
		buildComponent();
	}

	// ------------------------------------------------------------


	@SuppressWarnings("unchecked")
	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		setOutputMarkupId(true);
		view = new RepeatingView("listView");
		for (Object o : field.getFieldValues()) {
			IRBEntity entity = (IRBEntity) o;
			List<IRBEntity> entities = getServiceProvider().getRBEntityManagement()
				.findAll(entity.getRBMetaInfo().getSchemaID());
			listView = new ListView<IRBEntity>("resourceList", entities) {
				@Override
				protected void populateItem(final ListItem<IRBEntity> item) {
					IRBEntity e = (IRBEntity) item.getModelObject();
				}
			};
		}
//		final IModel<List<IRBEntity>> listModel = new ListModel(field.getFieldValues());
//		final List<IRBEntity> allEntites = (List<IRBEntity>) getServiceProvider().getRBEntityManagement().findAll(null);
//		add(createListView(listModel, allEntites));
//		add(new AddValueAjaxButton("button", field){
//
//			@Override
//			public void addField(final AjaxRequestTarget target, final Form<?> form) {
//				listView.getModelObject().add((DropDownChoice<IRBEntity>) new DropDownChoice<IRBEntity>("resourceList",
//						new PropertyModel(listModel, "" + (identifier++)),
//						allEntites, renderer).setOutputMarkupId(true));
//				target.add(listView.getParent());
//			}
//		});
	}

	/**
	 * Creates a {@link ListView} containing {@link DropDownChoice}.
	 *
	 * @param listModel	- instance of {@link ListModel}
	 * @param allEntites - List of all {@link IRBEntity} available of the resource-type
	 * @return A ListView of {@link DropDownChoice}
	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private ListView<DropDownChoice<IRBEntity>> createListView(
//			final IModel<List<IRBEntity>> listModel, final List<IRBEntity> allEntites) {
//		Use this to get the ResourceTypeId for IRBManagement to loadAllRBentites(uri)
//		ResourceID uri = null;
//		for (Constraint c : field.getConstraints()) {
//			uri = c.getResourceTypeConstraint();
//		}
//		renderer = new IChoiceRenderer<IRBEntity>() {
//			@Override
//			public Object getDisplayValue(final IRBEntity object) {
//				return object.toString();
//			}
//			@Override
//			public String getIdValue(final IRBEntity object, final int index) {
//				return object.getQualifiedName().toString();
//			}
//		};
//
//		ddcList.add(new DropDownChoice<IRBEntity>("resourceList",
//				new PropertyModel(listModel, "" + (identifier++)), allEntites, renderer));
//		listView = new ListView<DropDownChoice<IRBEntity>>("listView", ddcList) {
//			@Override
//			protected void populateItem(final ListItem<DropDownChoice<IRBEntity>> item) {
//				item.add(item.getModelObject());
//			}
//		};
//		listView.setReuseItems(true);
//		listView.setOutputMarkupId(true);
//		setOutputMarkupId(true);
//		return listView;
//	}


//	@Override
//	public CKComponent setViewMode(final ViewMode mode) {return null;}

}
