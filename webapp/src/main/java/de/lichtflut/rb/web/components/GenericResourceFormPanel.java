/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.infra.data.MultiMap;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.models.ResourceSchemaModel;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created May 6, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings({ "serial", "unchecked" })
public class GenericResourceFormPanel extends Panel {
	

	//Constructors
	
	/**
	 * 
	 */
	public GenericResourceFormPanel(String id, IModel model, ResourceSchema schema) {
		super(id, model);
		init(schema);
	}
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public GenericResourceFormPanel(String id, ResourceSchema schema) {
		super(id);
		init(schema);
	}

	
	// -----------------------------------------------------
	
	private void init(ResourceSchema schema){
		final ResourceSchemaModel rModel = ResourceSchemaModel.getInstance();
		final Form form = new Form("form"){
			@Override
			protected void onSubmit() {
				super.onSubmit();
				MultiMap<String, String> map = rModel.getMultiMap();
				StringBuilder string = new StringBuilder();
				for(String key : map.keySet()){
					String rightShift = "    ";
					string.append(key + ":<br />");
					for(String value : map.getValues(key)){
						string.append(rightShift + "value->" +  value + "<br />");
					}
				}
				info(string.toString());
			}
		};
		form.setOutputMarkupId(true);
		this.add(form);
		form.add(new FeedbackPanel("feedback").setEscapeModelStrings(false));
		if(schema!=null){
			final List<PropertyAssertion> assertions = new ArrayList<PropertyAssertion>(schema.getPropertyAssertions());
			form.add(new ListView("propertylist", assertions) {
			    protected void populateItem(final ListItem item) {
			        final PropertyAssertion assertion = (PropertyAssertion) item.getModelObject();
			        item.add(new Label("propertyLabel", assertion.getPropertyDeclaration().getName()));
			        item.add(getComponentForAssertion("propertyInput",assertion));
			    }
			    
				
				// -----------------------------------------------------
				
				private FormComponent getComponentForAssertion(String identifier, PropertyAssertion assertion){
					String attributeDescriptor = assertion.getPropertyDescriptor().getQualifiedName().toURI();  
					PropertyDeclaration decl = assertion.getPropertyDeclaration();
					  FormComponent component = null;
					  
					  switch(decl.getElementaryDataType()){
					  	case BOOLEAN : component = new TextField<String>(identifier,rModel.getModelForAttribute(attributeDescriptor)); break;
					  	case STRING : component = new TextField<String>(identifier,rModel.getModelForAttribute(attributeDescriptor)).setRequired(true); break;
					  	case INTEGER : component = new TextField<String>(identifier,rModel.getModelForAttribute(attributeDescriptor)); break;
					  	case RESOURCE : component = new TextField<String>(identifier,rModel.getModelForAttribute(attributeDescriptor)); break;
					  	case UNDEFINED : component = new TextField<String>(identifier,rModel.getModelForAttribute(attributeDescriptor)); break;
					  	case DATE : component = new TextField<String>(identifier,rModel.getModelForAttribute(attributeDescriptor)); break;
					  }

				      return component;
				}
			    
			});			
		}//End of if(schema==null)


	}//End of Method init
}
