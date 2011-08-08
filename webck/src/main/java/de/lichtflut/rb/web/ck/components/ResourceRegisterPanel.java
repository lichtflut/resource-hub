/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.RBEntityManagement;
import de.lichtflut.rb.core.api.RBEntityManagement.SearchContext;
import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;

/**
 * <p>
 * TODO: [DESCRIPTION].
 *
 * Supported Behaviors:
 * <ul>
 * <li>
 * SHOW_DETAILS: Occurence: Rendering each
 * value cell. param: field-name, entity.</li>
 *
 * </ul>
 *
 * </p>
 *
 * <p>
 * Created May 6, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings({ "serial", "unchecked" })
public abstract class ResourceRegisterPanel extends CKComponent{

	//Behavior-Keys
	/**
	 * <p>
	 * This behavior is called only when specified.
	 * The Behavior describes the action which will be executed by the link.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 * 	<li>{@link Class}, the ck-components-class</li>
	 * </ol>
	 * </p>
	 */
	public static final String SHOW_DETAILS = "de.lichtflut.web.ck.show_details.behavior";

	/**
	 * <p>
	 * This behavior is called only when specified.
	 * The Behavior describes the action which will be executed by the link.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 * 	<li>{@link Class}, the ck-components-class</li>
	 * </ol>
	 * </p>
	 */
	public static final String DELETE_ROW_ITEM = "de.lichtflut.web.ck.delete_row_item.behavior";

	/**
	 * <p>
	 * This behavior is called only when specified.
	 * The Behavior describes the action which will be executed by the link.
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 * 	<li>{@link Class}, the ck-components-class</li>
	 * </ol>
	 * </p>
	 */
	public static final String UPDATE_ROW_ITEM = "de.lichtflut.web.ck.update_row_item.behavior";

	/**
	 * <p>
	 * This behavior is called only when specified.
	 * The CKBehavior should return a CK-Component which will be added
	 * to the {@link ResourceRegisterPanel}
	 * </p>
	 * <p>
	 * The component-id must be "propertyField".
	 * </p>
	 * <p>
	 * The following params will be parsed to the behavior in a well-defined order:
	 * <ol>
	 * 	<li>{@link Class}, the ck-components-class</li>
	 * 	<li>String, the link label</li>
	 * </ol>
	 * </p>
	 */
	public static final String ADD_CUSTOM_ROW_ITEM = "de.lichtflut.web.ck.custom_row_item.behavior";

	@SuppressWarnings("rawtypes")
	private Collection<RBEntity> instances = new ArrayList<RBEntity>();
	private List<String> fields = new ArrayList<String>();
	private Collection<ResourceSchema> schemas = new ArrayList<ResourceSchema>();
	private String filter = "";
	private boolean simpleFlag;

	// Constructors

	/**
	 * @param id
	 *            /
	 * @param instances
	 *            /
	 * @param fields
	 *            /
	 * @param simpleFlag
	 *            /
	 */
	@SuppressWarnings("rawtypes")
	public ResourceRegisterPanel(final String id,
			final Collection<RBEntity> instances, final List<String> fields,
			final boolean simpleFlag) {
		super(id);
		this.instances = (ArrayList<RBEntity>) instances;
		this.fields = (ArrayList<String>) fields;
		this.simpleFlag = simpleFlag;
		buildComponent();
	}

	// -----------------------------------------------------

	/**
	 * Constructor.
	 * @param id
	 *            /
	 * @param schemas
	 *            /
	 * @param filter
	 *            /
	 * @param fields
	 *            /
	 * @param simpleFlag
	 *            /
	 *
	 */
	public ResourceRegisterPanel(final String id,
			final Collection<ResourceSchema> schemas, final String filter,
			final List<String> fields, final boolean simpleFlag) {
		super(id);
		this.filter = filter;
		this.fields = fields;
		this.simpleFlag = simpleFlag;
		this.schemas = schemas;
		buildComponent();
	}

	// -----------------------------------------------------

	/**
	 * @param view
	 *            /
	 * @return /
	 */
	public CKComponent setViewMode(final ViewMode view) {
		throw new NotYetImplementedException();

	}

	// -----------------------------------------------------

	/**
	 * <p>
	 *   If the param is true, this Component will display the simple
	 *   attribute names associated to the full qualified one. This is
	 *   only possible, if there is no explicit given field
	 *   specification This might end up in some redundancies
	 * </p>
	 * @param flag -
	 * @return {@link ResourceRegisterPanel} - self returing idiom
	 */
	public ResourceRegisterPanel enableSimpleFieldNames(final boolean flag) {
		throw new UnsupportedOperationException();
		// this.simpleFieldNamesEnabled=flag;
		// return this;
	}

	// -----------------------------------------------------

	/**
	 * @param entries
	 *            /
	 */
	private void init(final List<RegisterRowEntry> entries) {
		ListView<RegisterRowEntry> resourceTable = new ListView<RegisterRowEntry>(
				"resourceTable", entries) {
			@SuppressWarnings("rawtypes")
			protected void populateItem(final ListItem item){

				RegisterRowEntry entry = (RegisterRowEntry) item
						.getModelObject();
				ListView<Component> propertyLine = new ListView<Component>(
						"propertyLine", entry.getComponentList()) {
					protected void populateItem(final ListItem item) {
						item.add((Component) item.getModelObject());
					}
				};
				item.add(propertyLine);
			}
		};
		this.add(resourceTable);
	}

	/**
	 *
	 * @param schemas
	 *            /
	 * @param filter
	 *            /
	 * @param fields
	 *            /
	 * @param criteria
	 *            /
	 * @return /
	 */
	private List<RegisterRowEntry> buildRegisterTableEntries(
			final Collection<ResourceSchema> schemas, final String filter,
			final List<String> fields, final SortCriteria criteria) {
		RBEntityManagement rManagement = getServiceProvider()
				.getRBEntityManagement();
		if (filter != null && !filter.equals("")) {
			instances = rManagement.loadAllEntitiesForSchema(
					schemas, filter, SearchContext.CONJUNCT_MULTIPLE_KEYWORDS);
		} else {
			instances = rManagement
					.loadAllEntitiesForSchema(schemas);
		}
		return buildRegisterTableEntries(instances, fields, criteria);
	}

	// -----------------------------------------------------

	/**
	 * @param instances
	 *            /
	 * @param fields
	 *            /
	 * @param criteria
	 *            /
	 * @return /
	 */
	@SuppressWarnings("rawtypes")
	private List<RegisterRowEntry> buildRegisterTableEntries(
			final Collection<RBEntity> instances, final List<String> fields,
			final SortCriteria criteria) {
		List<RegisterRowEntry> output = new ArrayList<RegisterRowEntry>();
		List<String> tempFields = fields;
		if (tempFields == null || tempFields.size() == 0) {
			tempFields = evaluateTotalFields(instances);
		}
		// Add first the title-row
		output.add(new RegisterRowEntry(tempFields));

		for (RBEntity instance : instances) {
			output.add(new RegisterRowEntry(tempFields, instance));
		}
		return output;
	}

	// -----------------------------------------------------

	/**
	 * @param instances
	 *            /
	 * @return /
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList<String> evaluateTotalFields(
			final Collection<RBEntity> instances) {
		Map<String, Object> allreadyVisited = new HashMap<String, Object>();
		// Make a set to remove skip duplicates
		Set<String> fields = new HashSet<String>();
		for (RBEntity instance : instances) {
			if (allreadyVisited.get(instance.getResourceTypeID()
					.getQualifiedName().toURI()) == null) {
				allreadyVisited.put(instance.getResourceTypeID()
						.getQualifiedName().toURI(), Boolean.TRUE);
				Collection<String> attributeNames = instance.getAttributeNames();
				if (simpleFlag) {
					for (String attributeName : attributeNames) {
						fields.add(instance
								.getSimpleAttributeName(attributeName));
					}
				} else {
					fields.addAll(attributeNames);
				}
			}
		}
		return new ArrayList(fields);
	}


	// -----------------------------------------------------
	/**
 *
 */
	private class RegisterRowEntry {
		private ArrayList<Component> components = new ArrayList<Component>();

		/**
		 *
		 * @param fields
		 *            /
		 */
		public RegisterRowEntry(final List<String> fields) {
			this(fields, null);
		}

		// -----------------------------------------------------
		/**
		 * @param fields
		 *            /
		 * @param instance
		 *            /
		 */
		@SuppressWarnings("rawtypes")
		public RegisterRowEntry(final List<String> fields,
				final RBEntity instance) {
			components.clear();
			for (String field : fields) {
				if (instance == null) {
					// check if the field can be a simple name
					components.add(new Label("propertyField", Model.of(field)));
				} else {
					// Determine if the field is a simple attribute or not
					Collection values = instance.getValuesFor(field);
					if (values == null || values.isEmpty()) {
						values = new HashSet<Object>();
						Collection<String> attributes = instance
								.getAttributesNamesForSimple(field);
						for (String attr : attributes) {
							values.addAll(instance.getValuesFor(attr));
						}
					}
					String output = "";
					for (Object val : values) {
						output = output + ", " + val.toString();
					}
					// Cut of the trailing comma
					if (output.length() > 0) {
						output = output.substring(", ".length(),
								output.length());
					}
//					if (getBehavior(SHOW_DETAILS) != null) {
//						components.add((Component) getBehavior(SHOW_DETAILS)
//								.execute("propertyField", instance, output,
//										field));
//					} else {
						if(instance.isResourceNode()){
							CKLink link;
							link = new CKLink("propertyField", output, "http://google.de",
									CKLinkType.EXTERNAL_LINK);
							components.add(link);
						}else{
							components.add(new Label("propertyField", Model
								.of(output)));
						}
//					}
				}
			}

			if(getBehavior(SHOW_DETAILS) != null){
				final CKBehavior showDetails = getBehavior(SHOW_DETAILS);
				CKLink link = new CKLink("propertyField", "Details", CKLinkType.CUSTOM_BEHAVIOR);
				link.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior(){
					@Override
					public Object execute(final Object... objects) {
						showDetails.execute(instance);
						return null;
					}
				});
				components.add(link);
			}
			if(getBehavior(UPDATE_ROW_ITEM) != null){
				final CKBehavior updateDetails = getBehavior(UPDATE_ROW_ITEM);
				CKLink link = new CKLink("propertyField", "Update", CKLinkType.CUSTOM_BEHAVIOR);
				link.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior() {

					@Override
					public Object execute(final Object... objects) {
						updateDetails.execute(instance);
						return null;
					}
				});
				components.add(link);
			}
			if(getBehavior(DELETE_ROW_ITEM) != null){
				final CKBehavior deleteDetails = getBehavior(DELETE_ROW_ITEM);
				CKLink link = new CKLink("propertyField", "Delete", CKLinkType.CUSTOM_BEHAVIOR);
				link.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior() {

					@Override
					public Object execute(final Object... objects) {
						deleteDetails.execute(instance);
						return null;
					}
				});
				components.add(link);
			}
			if(getBehavior(ADD_CUSTOM_ROW_ITEM) != null){
				CKBehavior behavior = getBehavior(ADD_CUSTOM_ROW_ITEM);
				components.add((Component) behavior.execute(this));
			}
		}

		// -----------------------------------------------------

		/**
		 * @param /
		 * @return /
		 */
		public ArrayList<Component> getComponentList() {
			return components;
		}
	}

	// -----------------------------------------------------

	/**
	 *
	 */
	private class SortCriteria {

	}

	// -----------------------------------------------------

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		List<RegisterRowEntry> entries=null;
		try{
		if(instances != null && instances.size() > 0){
			entries = buildRegisterTableEntries(
					instances,
					fields, null);
		}else{
			entries = buildRegisterTableEntries(
					schemas,
					filter,
					fields, null);
		}
		//If something went wrong
		}catch(Exception any){
			getLogger().error(this.getClass().getName() + " could not be initalized ", any);
			entries=null;
		}
		init(entries);
	}
}
