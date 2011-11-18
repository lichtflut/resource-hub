/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SNValue;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.webck.application.LinkProvider;
import de.lichtflut.rb.webck.behavior.CKBehavior;
import de.lichtflut.rb.webck.components.editor.EntityPanel;

/**
 * <p>
 * This Panel displays a List of {@link RBEntity}s with all their Fields.
 * Selected CKBehaviors cann be added to this Panel.
 *</p>
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public abstract class ResourceTableView extends CKComponent {

	//TODO: REFACTOR
	// Behavior-Keys
	/**
	 * <p>
	 * This behavior is called only when specified. The Behavior describes the
	 * action which will be executed by the link.
	 * </p>
	 * <p>
	 * If <code>null</code> is returned, the default behavior will be executed.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined
	 * order:
	 * <ol>
	 * <li>{@link String}, wicket-id</li>
	 * <li>{@link RBEntity}, the RBEntity</li>
	 * <li>{@link CKComponent} this Component</li>
	 * </ol>
	 * </p>
	 */
	public static final String SHOW_DETAILS = "de.lichtflut.web.ck.show_details.behavior";

	/**
	 * <p>
	 * This behavior is called only when specified. The Behavior describes the
	 * action which will be executed by the link.
	 * </p>
	 * <p>
	 * If <code>null</code> is returned, the default behavior will be executed.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined
	 * order:
	 * <ol>
	 * <li>{@link String}, wicket-id</li>
	 * <li>{@link RBEntity}, the RBEntity</li>
	 * <li>{@link CKComponent} this Component</li>
	 * </ol>
	 * </p>
	 */
	public static final String UPDATE_ROW_ITEM = "de.lichtflut.web.ck.update_row_item.behavior";

	/**
	 * <p>
	 * This behavior is called only when specified. The Behavior describes the
	 * action which will be executed by the link.
	 * </p>
	 * <p>
	 * If <code>null</code> is returned, the default behavior will be executed.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined
	 * order:
	 * <ol>
	 * <li>{@link String}, wicket-id</li>
	 * <li>{@link RBEntity}, the RBEntity</li>
	 * <li>{@link CKComponent} this Component</li>
	 * </ol>
	 * </p>
	 */
	public static final String DELETE_ROW_ITEM = "de.lichtflut.web.ck.delete_row_item.behavior";

	/**
	 * <p>
	 * This behavior is executed when an attribute of type "resource" is
	 * encountered. It must return a {@link Component} which will be added to
	 * the {@link ResourceRegisterPanel}.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined
	 * order:
	 * <ol>
	 * <li>{@link String}, wicket-id</li>
	 * <li>{@link RBEntity}, the RBEntity</li>
	 * <li>{@link RBField} instance of {@link RBField}</li>
	 * </ol>
	 * </p>
	 */
	public static final String RESOURCE_FIELD_BEHAVIOR = "de.lichtflut.web.ck.resource_field_behavior.behavior";

	/**
	 * <p>
	 * This behavior is called only when specified. The CKBehavior musst return
	 * a {@link Component} which will be added to the {@link ResourceTableView}
	 * </p>
	 * <p>
	 * The component-id must be "value".
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined
	 * order:
	 * <ol>
	 * <li>{@link String}, wicket-id</li>
	 * <li>{@link RBEntity}, the RBEntity</li>
	 * <li>{@link RBField} instance of {@link RBField}</li>
	 * </ol>
	 * </p>
	 */
	public static final String ADD_CUSTOM_ROW_ITEM = "de.lichtflut.web.ck.custom_row_item.behavior";
	
	// -----------------------------------------------------

	private Map<ResourceID, String> tableHeader = new HashMap<ResourceID, String>();
	private List<RBEntity> entites = new ArrayList<RBEntity>();
	private String componentID;

	/**
	 * Constructor.
	 *
	 * @param id - wicket:id
	 * @param entites - List of {@link RBEntity}
	 */
	public ResourceTableView(final String id, final List<RBEntity> entites) {
		super(id);
		this.entites = entites;
		this.componentID = id;
		buildComponent();
	}

	/**
	 * @param entites
	 *            -
	 */
	@SuppressWarnings("rawtypes")
	private void addRows(final List<RBEntity> entites) {
		ListView<RBEntity> view = new ListView<RBEntity>("row", entites) {
			@Override
			protected void populateItem(final ListItem item) {
				final RBEntity e = (RBEntity) item.getModelObject();
				List<Object> fields = sortFieldsForPresentation(e);
				// Add CK-Behaviors if set.
				if (getBehavior(SHOW_DETAILS) != null) {
					fields.add(SHOW_DETAILS);
				}
				if (getBehavior(UPDATE_ROW_ITEM) != null) {
					fields.add(UPDATE_ROW_ITEM);
				}
				if (getBehavior(DELETE_ROW_ITEM) != null) {
					fields.add(DELETE_ROW_ITEM);
				}
				if (getBehavior(ADD_CUSTOM_ROW_ITEM) != null) {
					fields.add(ADD_CUSTOM_ROW_ITEM);
				}
				ListView<Object> data = new ListView<Object>("cell", fields) {
					@Override
					protected void populateItem(final ListItem item) {
						addItem(e, item);
					}

				};
				item.add(data);
			}
		};
		this.add(view);
	}

	/**
	 * Adds the content of each cell.
	 * @param entity - instance of {@link RBEntity}. This is used only
	 * 				for behaviors to provide additional information to the developer.
	 * 				See CKBehavior for details.
	 * @param item - {@link ListItem} to be displayed
	 */
	@SuppressWarnings("rawtypes")
	private void addItem(final RBEntity entity, final ListItem item) {
		// If ListItem is instance of IRBField:
		//		- display all fieldvalues.
		// Else if ListItem is instance of String and equals to one of the
		// above declared CKBehavior-keys:
		//		- it will execute the behavior.
		if (item.getModelObject() instanceof RBField) {
			RBField field = (RBField) item.getModelObject();
			boolean isResource = false;
			for (Constraint c : field.getConstraints()) {
				if (c.isResourceTypeConstraint()) {
					isResource = true;
				}
			}

			if ((getBehavior(RESOURCE_FIELD_BEHAVIOR) != null)
					&& (field.isResourceReference())) {
				item.add((Component) getBehavior(RESOURCE_FIELD_BEHAVIOR)
						.execute("data", entity, field));
			} else {
				if (isResource) {
					RepeatingView view = new RepeatingView("data");
					int resourceCount = 1;
					for (final Object entityAttribute : field.getValues()) {
						final RBEntity currentEntity = (RBEntity) entityAttribute;
						if(currentEntity != null){
							CKLink link = new CKLink(view.newChildId(), currentEntity.getQualifiedName().toURI(),
									CKLinkType.CUSTOM_BEHAVIOR);
							link.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior() {
								@Override
								public Object execute(final Object... objects) {
									onShowDetails(currentEntity.getID());
									return null;
								}
							});
							view.add(link);
							if(resourceCount < field.getValues().size()){
								view.add(new Label(view.newChildId(), ", "));
							}
							resourceCount++;
						}else{
							view.add(new Label(view.newChildId(), ""));
						}
					}
					item.add(view);
				} else {
					String output = "";
					if(field.getDataType().equals(ElementaryDataType.DATE)){
						for (Object o : field.getValues()) {
							SNValue date = (SNValue) o;
							if(date != null){
								output = output.concat(DateFormat.getDateInstance(DateFormat.SHORT)
										.format(date.getTimeValue()));
							}
						}
						item.add(new Label("data", output));
					}else{
						for (Object s : field.getValues()) {
							if (s == null) {
								s = "";
							}
							output = output.concat(s.toString() + ", ");
						}
						// Cut of last ", "
						if(output.length() > 0){
							output = output.substring(0, output.length() - 2);
						}
						item.add(new Label("data", output));
					}
				}
			}
		} else if (item.getModelObject() instanceof String) {
			String s = (String) item.getModelObject();
			Fragment f = new Fragment("data", "detailsLink", this);
			Link link = null;
			if (s.equals(SHOW_DETAILS)) {
				if(getBehavior(s).execute("featureLink", entity, ResourceTableView.this) == null){
				link = new Link("featureLink") {
					@Override
					public void onClick() {
						onShowDetails(entity.getID());
					}
				};
				link.add(new Label("label", "Details"));
				f.add(link);
				}else{
					f.add((Component) getBehavior(s).execute("featureLink", entity,ResourceTableView.this));
				}
			}else if(s.equals(UPDATE_ROW_ITEM)){
				if(getBehavior(s).execute("featureLink", entity, ResourceTableView.this) == null){
				link = new Link("featureLink") {
					@Override
					public void onClick() {
						ResourceTableView.this.replaceWith(new EntityPanel(componentID, Model.of(entity), Model.of(false)) {
							@Override
							public EntityManager getEntityManager() {
								return getServiceProvider().getEntityManager();
							}
							@Override
							public LinkProvider getLinkProvider() {
								return null;
							}
							
						});
					}
				};
				link.add(new Label("label", "Update"));
				f.add(link);
				}else{
					f.add((Component) getBehavior(s).execute("details", entity,ResourceTableView.this));
				}
			}else if(s.equals(DELETE_ROW_ITEM)){
				if(getBehavior(s).execute("featureLink", entity,ResourceTableView.this) == null){
				link = new Link("featureLink") {
					@Override
					public void onClick() {
						getServiceProvider().getEntityManager().delete(entity);
					}
				};
				link.add(new Label("label", "Delete"));
				f.add(link);
				}else{
					f = new Fragment("data", "customLink", this);
					f.add((Component) getBehavior(s).execute("customComponent", entity, ResourceTableView.this));
				}
			}else if (s.equals(ADD_CUSTOM_ROW_ITEM)) {
				f = new Fragment("data", "customLink", this);
				f.add((Component) getBehavior(s).execute("customComponent", entity));
			}
			item.add(f);
		}else if (item.getModelObject() == null) {
				item.add(new Label("data", ""));
		}
	}
	
	protected abstract void onShowDetails(final ResourceID selected);

	/**
	 * Sorts the IRBFields according to the table header.
	 *
	 * @param e
	 *            - instance of {@link RBEntity}
	 * @return list of {@link RBField}
	 */
	private List<Object> sortFieldsForPresentation(final RBEntity e) {
		List<Object> fields = new ArrayList<Object>();
		for (ResourceID predicate : tableHeader.keySet()) {
			RBField field = e.getField(predicate);
			fields.add(field);
		}
		return fields;
	}

	/**
	 * Fills the Tableheader with the appropriate Label.
	 */
	@SuppressWarnings({ "rawtypes" })
	private void addHeader() {
		// Create List for ListView
		List<String> titles = new ArrayList<String>();
		for (String string : tableHeader.values()) {
			titles.add(string);
		}
		if (getBehavior(SHOW_DETAILS) != null) {
			titles.add("Details");
		}
		if (getBehavior(UPDATE_ROW_ITEM) != null) {
			titles.add("Update");
		}
		if (getBehavior(DELETE_ROW_ITEM) != null) {
			titles.add("Delete");
		}
		if (getBehavior(ADD_CUSTOM_ROW_ITEM) != null) {
			titles.add("Other");
		}
		this.add(new ListView<String>("tableHeader", titles) {
			@Override
			protected void populateItem(final ListItem item) {
				item.add(new Label("headerData", (String) item.getModelObject()));
			}
		});
	}

	/**
	 * Indexes all the {@link RBField} names.
	 *
	 * @param entites
	 *            -
	 */
	private void indexTableHeader(final List<RBEntity> entites) {
		tableHeader.clear();
		for (RBEntity entity : entites) {
			for (RBField field : entity.getAllFields()) {
				if (!tableHeader.containsKey(field.getPredicate())) {
					tableHeader.put(field.getPredicate(), field.getLabel());
				}
			}
		}
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		this.setEscapeModelStrings(false);
		indexTableHeader(entites);
		addHeader();
		addRows(entites);
	}

}
