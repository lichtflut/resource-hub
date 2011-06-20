/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.genericresource.panels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.RBEntityManagement;
import de.lichtflut.rb.core.api.RBEntityManagement.SearchContext;
import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
import de.lichtflut.rb.web.ck.components.CKComponent;

/**
 * <p>
 * TODO: [DESCRIPTION].
 * 
 * Supported Behaviors:
 * <ul>
 * <li>
 * <<<<<<< Updated upstream SHOW_DETAILS: Occurence: Rendering each value cell.
 * param: field-name, entity ======= SHOW_DETAILS: Occurence: Rendering each
 * value cell. param: field-name, entity. >>>>>>> Stashed changes</li>
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
public abstract class ResourceRegisterPanel extends CKComponent {

	private String SHOW_DETAILS = "xyz";

	public String getSHOW_DETAILS() {
		return SHOW_DETAILS;
	}

	public void setSHOW_DETAILS(String sHOW_DETAILS) {
		SHOW_DETAILS = sHOW_DETAILS;
	}

	private Boolean simpleFieldNamesEnabled = false;

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
		this.simpleFieldNamesEnabled = simpleFlag;
		List<RegisterRowEntry> entries = buildRegisterTableEntries(instances,
				fields, null);
		init(entries);
	}

	// -----------------------------------------------------

	/**
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

		addBehavior(SHOW_DETAILS, new CKBehavior() {
			@SuppressWarnings({ "rawtypes", "unused" })
			@Override
			public Object execute(final Object... objects) {
				String identifier = (String) objects[0];
				String value = (String) objects[2];
				final RBEntity instance = (RBEntity) objects[1];
				if (value.contains("a")) {
					return new ExternalLink(identifier, Model
							.of("http://google.com?q=" + value), Model
							.of("google"));
				} else {
					return new Label(identifier, value);
				}
			}
		});

		this.simpleFieldNamesEnabled = simpleFlag;
		List<RegisterRowEntry> entries = buildRegisterTableEntries(schemas,
				filter, fields, null);
		init(entries);
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
	 * @param flag
	 *            ,
	 *            <p>
	 *            If the param is true, this Component will display the simple
	 *            attribute names associated to the full qualified one. This is
	 *            only possible, if there is no explicit given field
	 *            specification This might end up in some redundancies
	 *            </p>
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
		this.removeAll();
		ListView<RegisterRowEntry> resourceTable = new ListView<RegisterRowEntry>(
				"resourceTable", entries) {
			@SuppressWarnings("rawtypes")
			protected void populateItem(ListItem item) {

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
	@SuppressWarnings("rawtypes")
	private List<RegisterRowEntry> buildRegisterTableEntries(
			final Collection<ResourceSchema> schemas, final String filter,
			final List<String> fields, final SortCriteria criteria) {
		RBEntityManagement rManagement = getServiceProvider()
				.getRBEntityManagement();
		Collection<RBEntity> instances;
		if (filter != null && !filter.equals("")) {
			instances = rManagement.loadAllResourceTypeInstancesForSchema(
					schemas, filter, SearchContext.CONJUNCT_MULTIPLE_KEYWORDS);
		} else {
			instances = rManagement
					.loadAllResourceTypeInstancesForSchema(schemas);
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

		// Add first the tile-row
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
				Collection<String> attributeNames = instance
						.getAttributeNames();
				if (this.simpleFieldNamesEnabled) {
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
					if (getBehavior(SHOW_DETAILS) != null) {
						components.add((Component) getBehavior(SHOW_DETAILS)
								.execute("propertyField", instance, output,
										field));
					} else {
						components.add(new Label("propertyField", Model
								.of(output)));
					}
				}
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

}
