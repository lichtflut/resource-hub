/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

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

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.spi.INewRBServiceProvider;
import de.lichtflut.rb.core.spi.RBServiceProvider;

/**
 * <p>
 * This Panel displays a List of {@link IRBEntity}s with all their Fields.
 * Selected CKBehaviors cann be added to this Panel.
 *</p>
 * Created: Aug 18, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public abstract class ResourceTableView extends CKComponent {

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
	 * <li>{@link IRBEntity}, the RBEntity</li>
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
	 * <li>{@link IRBEntity}, the RBEntity</li>
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
	 * <li>{@link IRBEntity}, the RBEntity</li>
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
	 * <li>{@link IRBEntity}, the RBEntity</li>
	 * <li>{@link IRBField} instance of {@link IRBField}</li>
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
	 * <li>{@link IRBEntity}, the RBEntity</li>
	 * <li>{@link IRBField} instance of {@link IRBField}</li>
	 * </ol>
	 * </p>
	 */
	public static final String ADD_CUSTOM_ROW_ITEM = "de.lichtflut.web.ck.custom_row_item.behavior";

	private Map<String, String> tableHeader = new HashMap<String, String>();
	private List<IRBEntity> entites = new ArrayList<IRBEntity>();
	private String componentID;

	/**
	 * Constructor.
	 *
	 * @param id - wicket:id
	 * @param entites - List of {@link IRBEntity}
	 */
	public ResourceTableView(final String id, final List<IRBEntity> entites) {
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
	private void addRows(final List<IRBEntity> entites) {
		ListView<IRBEntity> view = new ListView<IRBEntity>("row", entites) {
			@Override
			protected void populateItem(final ListItem item) {
				final IRBEntity e = (IRBEntity) item.getModelObject();
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
	 * @param e - instance of {@link IRBEntity}. This is used only
	 * 				for behaviors to provide additional information to the developer.
	 * 				See CKBehavior for details.
	 * @param item - {@link ListItem} to be displayed
	 */
	@SuppressWarnings("rawtypes")
	private void addItem(final IRBEntity e, final ListItem item) {
		// If ListItem is instance of IRBField:
		//		- display all fieldvalues.
		// Else if ListItem is instance of String and equals to one of the
		// above declared CKBehavior-keys:
		//		- it will execute the behavior.
		if (item.getModelObject() instanceof IRBField) {
			IRBField field = (IRBField) item.getModelObject();
			String output = "";
			for (Object s : field.getFieldValues()) {
				output += s;
			}
			if (getBehavior(ADD_CUSTOM_ROW_ITEM) != null) {
				item.add((Component) getBehavior(ADD_CUSTOM_ROW_ITEM).execute(
						"data", e, field));
			} else if ((getBehavior(RESOURCE_FIELD_BEHAVIOR) != null)
					&& (field.isResourceReference())) {
				item.add((Component) getBehavior(RESOURCE_FIELD_BEHAVIOR)
						.execute("data", e, field));
			} else {
				item.add(new Label("data", output));
			}
		} else if (item.getModelObject() instanceof String) {
			String s = (String) item.getModelObject();
			Fragment f = new Fragment("data", "detailsLink", this);
			Link link = null;
			if (s.equals(SHOW_DETAILS)) {
				if(getBehavior(s).execute("featureLink", e, ResourceTableView.this) == null){
				link = new Link("featureLink") {
					@Override
					public void onClick() {
						ResourceTableView.this.replaceWith(new ResourceDetailPanel(componentID, e) {
							@Override
							public CKComponent setViewMode(final ViewMode mode) {return null;}
							@Override
							protected void initComponent(final CKValueWrapperModel model) {}
							@Override
							public RBServiceProvider getServiceProvider() {return null;}
							@Override
							public INewRBServiceProvider getNewServiceProvider() {return null;}
						});
					}
				};
				link.add(new Label("label", "Details"));
				f.add(link);
				}else{
					f.add((Component) getBehavior(s).execute("featureLink", e,ResourceTableView.this));
				}
			}else if(s.equals(UPDATE_ROW_ITEM)){
				if(getBehavior(s).execute("featureLink", e, ResourceTableView.this) == null){
				link = new Link("featureLink") {
					@Override
					public void onClick() {
						ResourceTableView.this.replaceWith(new ResourceDetailPanel(componentID, e) {
							@Override
							public CKComponent setViewMode(final ViewMode mode) {return null;}
							@Override
							protected void initComponent(final CKValueWrapperModel model) {}
							@Override
							public RBServiceProvider getServiceProvider() {return null;}
							@Override
							public INewRBServiceProvider getNewServiceProvider() {return null;}
						});
					}
				};
				link.add(new Label("label", "Update"));
				f.add(link);
				}else{
					f.add((Component) getBehavior(s).execute("details", e,ResourceTableView.this));
				}
			}else if(s.equals(DELETE_ROW_ITEM)){
				if(getBehavior(s).execute("featureLink", e,ResourceTableView.this) == null){
				link = new Link("featureLink") {
					@Override
					public void onClick() {
						getNewServiceProvider().getRBEntityManagement().delete(e);
					}
				};
				link.add(new Label("label", "Delete"));
				f.add(link);
				}else{
					f = new Fragment("data", "customLink", this);
					f.add((Component) getBehavior(s).execute("customComponent", e, ResourceTableView.this));
				}
			}
			item.add(f);
		}
	}

	/**
	 * Sorts the IRBFields according to the table header.
	 *
	 * @param e
	 *            - instance of {@link IRBEntity}
	 * @return list of {@link IRBField}
	 */
	private List<Object> sortFieldsForPresentation(final IRBEntity e) {
		List<Object> fields = new ArrayList<Object>();
		for (String s : tableHeader.keySet()) {
			fields.add(e.getField(s));
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
		this.add(new ListView<String>("tableHeader", titles) {
			@Override
			protected void populateItem(final ListItem item) {
				item.add(new Label("headerData", (String) item.getModelObject()));
			}
		});
	}

	/**
	 * Indexes all the {@link IRBField} names.
	 *
	 * @param entites
	 *            -
	 */
	private void indexTableHeader(final List<IRBEntity> entites) {
		tableHeader.clear();
		for (IRBEntity entity : entites) {
			for (IRBField field : entity.getAllFields()) {
				if (!tableHeader.containsKey(field.getFieldName())) {
					tableHeader.put(field.getFieldName(), field.getLabel());
				}
			}
		}
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		indexTableHeader(entites);
		addHeader();
		addRows(entites);
	}
}