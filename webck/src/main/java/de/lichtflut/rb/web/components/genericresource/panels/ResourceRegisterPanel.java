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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.ResourceTypeManagement;
import de.lichtflut.rb.core.api.ResourceTypeManagement.SearchContext;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.web.components.genericresource.GenericResourceComponent;

/**
 * <p>
 *  TODO: [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created May 6, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings({ "serial", "unchecked" })
public abstract class ResourceRegisterPanel extends Panel implements GenericResourceComponent{
	
	//Constructors
	
	/**
	 * 
	 */
	public ResourceRegisterPanel(String id, final Collection<ResourceTypeInstance> instances, ArrayList<String> fields){
		super(id);
		List<RegisterRowEntry> entries = buildRegisterTableEntries(instances, fields, null);
		init(entries);
	}
	
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public ResourceRegisterPanel(String id, final Collection<ResourceSchema> schemas, final String filter, ArrayList<String> fields) {
		super(id);
		List<RegisterRowEntry> entries = buildRegisterTableEntries(schemas, filter, fields, null);
		init(entries);
	}


	// -----------------------------------------------------

	public GenericResourceComponent setViewMode(ViewMode view){
		throw new NotYetImplementedException();
		
	}
	
	
	// -----------------------------------------------------
	
	private void init(final List<RegisterRowEntry> entries){
		ListView<RegisterRowEntry> resourceTable = new ListView<RegisterRowEntry>("resourceTable", entries){
			protected void populateItem(ListItem item) {
				RegisterRowEntry entry = (RegisterRowEntry) item.getModelObject();
				ListView<Component> propertyLine = new ListView<Component>("propertyLine", entry.getComponentList()){
					protected void populateItem(ListItem item) {
						item.add((Component) item.getModelObject());
					}
				};
				item.add(propertyLine);
			}
			
		};
		this.add(resourceTable);
	}
	
	
	private List<RegisterRowEntry> buildRegisterTableEntries(Collection<ResourceSchema> schemas,
			String filter,ArrayList<String> fields, SortCriteria criteria) {
		ResourceTypeManagement rManagement = getServiceProvider().getResourceTypeManagement();
		Collection<ResourceTypeInstance> instances;
		if(filter==null || filter.equals("")){
			instances =	rManagement.loadAllResourceTypeInstancesForSchema(schemas, filter,SearchContext.CONJUNCT_MULTIPLE_KEYWORDS);
		}else{
			instances = rManagement.loadAllResourceTypeInstancesForSchema(schemas);
		}
		return buildRegisterTableEntries(instances, fields, criteria);
	}

	// -----------------------------------------------------
	
	
	private List<RegisterRowEntry> buildRegisterTableEntries(Collection<ResourceTypeInstance> instances,
			ArrayList<String> fields, SortCriteria criteria) {
		List<RegisterRowEntry> output = new ArrayList<RegisterRowEntry>();
		if(fields==null || fields.size()==0){
			fields = evaluateTotalFields(instances);
		}
	
		//Add first the tile-row
		output.add(new RegisterRowEntry(fields));
		
		for (ResourceTypeInstance instance : instances) {
			output.add(new RegisterRowEntry(fields, instance));
		}
		return output;
	}
	
	// -----------------------------------------------------
	
	private ArrayList<String> evaluateTotalFields(Collection<ResourceTypeInstance> instances) {
		Map<String, Object> allreadyVisited = new HashMap<String, Object>();
		//Make a set to remove skip duplicates
		Set<String> fields = new HashSet<String>();
		for (ResourceTypeInstance instance : instances) {
			if(allreadyVisited.get(instance.getResourceTypeID().getQualifiedName().toURI())==null){
				allreadyVisited.put(instance.getResourceTypeID().getQualifiedName().toURI(), Boolean.TRUE);
				fields.addAll(instance.getAttributeNames());
			}
		}
		return new ArrayList(fields);
	}


	// -----------------------------------------------------
	
	
	private class RegisterRowEntry{
		private ArrayList<Component> components = new ArrayList<Component>(); 
		
		public RegisterRowEntry(ArrayList<String> fields){
			this(fields, null);
		}
		
		// -----------------------------------------------------
		
		public RegisterRowEntry(ArrayList<String> fields, ResourceTypeInstance instance){
			components.clear();
			for (String field : fields) {
				if(instance==null){
					components.add(new Label("propertyField", Model.of(field)));
				}else{
					//Determine if the field is a simple attribute or not
					Collection values = instance.getValuesFor(field);
					if(values==null || values.isEmpty()){
						values = new HashSet<Object>();
						Collection<String> attributes = instance.getAttributesNamesForSimple(field);
						for (String attr : attributes) {
							values.addAll(instance.getValuesFor(attr));
						}
					}
					String output="";
					for (Object val : values) {
						output = val.toString();
					}
					components.add(new Label("propertyField", Model.of(output)));
				}
			}
		}
		
		// -----------------------------------------------------
		
		public ArrayList<Component> getComponentList(){
			return components;
		}
		
	}
	
	// -----------------------------------------------------
	
	private class SortCriteria{
		
	}
	
}
