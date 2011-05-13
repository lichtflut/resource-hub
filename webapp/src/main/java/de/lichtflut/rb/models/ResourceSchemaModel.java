/**
 * 
 */
package de.lichtflut.rb.models;


import org.apache.wicket.model.IModel;
import java.lang.String;
import java.util.ArrayList;
import java.util.Set;

import de.lichtflut.infra.data.MultiMap;

/**
 * @author Nils Bleisch
 *
 */
@SuppressWarnings({ "serial" })
public class ResourceSchemaModel implements IModel<String> {

	public static ResourceSchemaModel getInstance(){
		return new ResourceSchemaModel();
	}
	
	private String selected_attribute = null;
	private int index = 0;
	private MultiMap<String,String> map = new MultiMap<String, String>();
	
	private ResourceSchemaModel(){
		
	}
	
	// -----------------------------------------------------
	
	public String getObject() {
		String output = null;
		Set<String> values = map.getValues(selected_attribute);
		if(values!=null){
			if(values.size()>= (index+1)){
				output = (String) values.toArray()[index];
			}
		}
		return output;
	}

	// -----------------------------------------------------
	
	public void setObject(String object) {
		
		//Replace exisiting entry
		ArrayList<String> values = new ArrayList<String>(map.remove(selected_attribute));
		if(values.size() <= this.index)  values.add(this.index, object);
		else values.set(this.index, object);
		map.addAll(selected_attribute, values);
	}
	
	// -----------------------------------------------------
	
	public void detach() {
		//map = new MultiMap<String, String>();
		
	}
	
	// -----------------------------------------------------
	
	public ResourceSchemaModel getModelForAttribute(String attribute){
		ResourceSchemaModel model = new ResourceSchemaModel();
		model.index=0;
		model.selected_attribute=attribute;
		model.setAttributeValueCollection(this.map);
		
		//setting up the models index
		Set<String> values = model.map.getValues(selected_attribute);
		if(values!=null){
			model.index = (values.size());
		}	 
		//initializing this attribute in collection
		model.map.add(model.selected_attribute,"");
		return model;
	}
	
	// -----------------------------------------------------
	
	public MultiMap<String, String> getMultiMap(){
		return this.map;
	}
	
	// -----------------------------------------------------
	
	private void setAttributeValueCollection(MultiMap<String,String> map){
		this.map = map;
	}
	
	

}
