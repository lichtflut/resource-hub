/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;

import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

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
public class GenericFormPanel extends Panel {
	

	//Constructors
	
	/**
	 * 
	 */
	public GenericFormPanel(String id, IModel model, ResourceSchema schema) {
		super(id, model);
		init(schema);
	}
	
	// -----------------------------------------------------
	
	/**
	 * 
	 */
	public GenericFormPanel(String id, ResourceSchema schema) {
		super(id);
		init(schema);
	}

	
	
	// -----------------------------------------------------
	
	private void init(ResourceSchema schema){
		final Form form = new Form("form");
		final WebMarkupContainer superRootContainer = new WebMarkupContainer("superRootContainer");
		superRootContainer.setOutputMarkupId(true);
		form.setOutputMarkupId(true);
		if(schema!=null){
			final List<PropertyAssertion> assertions = new ArrayList<PropertyAssertion>(schema.getPropertyAssertions());
			
			
			
			superRootContainer.add(new ListView("propertylist", assertions) {
			    protected void populateItem(final ListItem item) {
			        final PropertyAssertion assertion = (PropertyAssertion) item.getModelObject();
			        final WebMarkupContainer rootContainer = new WebMarkupContainer("rootContainer");
			        item.add(new Label("propertyLabel", assertion.getPropertyDeclaration().getName()));
			        
			        
			        rootContainer.setOutputMarkupId(true);
			        
			        final RepeatingView inputList = new RepeatingView("inputList");
			        
			        int min = assertion.getCardinality().getMinOccurs();
			        for(int x= 0; x<min; x++){
				        WebMarkupContainer container = new WebMarkupContainer(inputList.newChildId());
				        container.add(getFragmentForAssertion(assertion));
				        container.setOutputMarkupId(true);
				        inputList.add(container);
			        }
			       

			        AjaxSubmitLink addLink = new AjaxSubmitLink("addRow", form) {
			        	   int max = assertion.getCardinality().getMaxOccurs();
			        	   int min = assertion.getCardinality().getMinOccurs();
			        	   int current = assertion.getCardinality().getMinOccurs();
			        		
			        	   @Override
			        	   public void onSubmit(AjaxRequestTarget target, Form<?> form) {
			        		if(current<max){
			        			current+=1;
			        		}else{
			        			//do nothing
			        		}
			       	        WebMarkupContainer container = new WebMarkupContainer(inputList.newChildId());
			       	        container.setOutputMarkupId(true);
					        container.add(getFragmentForAssertion(assertion));
					        inputList.add(container);
					        if(target!=null){	
					        	target.add(container);
					        	target.add(form);
					        	target.add(rootContainer);
					        	target.add(superRootContainer);
					        }
			        	   }

						protected void onError(AjaxRequestTarget target,
								Form<?> form) {
							//do nothing				
						}
						
			
			        };
			        addLink.setDefaultFormProcessing(false);
			        rootContainer.add(inputList);
			        item.add(rootContainer);
			        item.add(addLink);
			       
			    }
			    
				
				// -----------------------------------------------------
				
				private Fragment getFragmentForAssertion(PropertyAssertion assertion){
					  PropertyDeclaration decl = assertion.getPropertyDeclaration();
					  Fragment fragment=null;
					  
					  switch(decl.getElementaryDataType()){
					  	case BOOLEAN : fragment = addCheckBoxFragment(decl); break;
					  	case STRING : fragment = addTextBoxFragment(decl); break;
					  	case INTEGER : fragment = addTextBoxFragment(decl); break;
					  	case RESOURCE : fragment = addResourceBoxFragment(decl); break;
					  	case UNDEFINED : fragment = addTextBoxFragment(decl); break;
					  	case DATE : fragment = addCalendarFragment(decl); break;
					  }
					
				      return fragment;
				}

				/* -----------------------------------------------------
				-----------------Santa's little helper------------------
				-------------------------------------------------------*/
				
				private Fragment addCalendarFragment(PropertyDeclaration decl) {
					Fragment fragment = new Fragment("propertyInput","dateInput", this);
					TextField field = new TextField("dateField", Date.class);
					fragment.add(field);
					//org.apache.wicket.extensions.
					//fragment.add(new DatePicker("dateFieldPicker", field));
				    return fragment;
				}

				// -----------------------------------------------------
				
				private Fragment addResourceBoxFragment(PropertyDeclaration decl) {
					Fragment fragment = new Fragment("propertyInput","textInput", this);
				     fragment.add(new TextField<String>("text", Model.of("")));
				     return fragment;
				}

				// -----------------------------------------------------
				
				private Fragment addTextBoxFragment(PropertyDeclaration decl) {
				     Fragment fragment = new Fragment("propertyInput","textInput", this);
				     fragment.add(new TextField<String>("text", Model.of("")));
				     return fragment;
				}

				// -----------------------------------------------------
				
				private Fragment addCheckBoxFragment(PropertyDeclaration decl) {
					Fragment fragment = new Fragment("propertyInput","checkboxInput", this);
					fragment.add(new CheckBox("checkbox"));
				    return fragment;
				}
				
				// -----------------------------------------------------
				
			    
			    
			});			
		}
		
		this.add(new Label("title", Model.of(schema.getDescribedResourceID().getQualifiedName().getSimpleName())));
		form.add(superRootContainer);
		this.add(form);
	}


}
