/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import de.lichtflut.rb.core.schema.model.RBInvalidValueException;
import de.lichtflut.rb.core.schema.model.RBValidator;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;

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
		final ResourceTypeInstance instance = schema.generateTypeInstance();
		final Form form = new Form("form"){
			@Override
			protected void onSubmit() {
				super.onSubmit();
				Collection<String> attributes = instance.getAttributeNames();
				StringBuilder string = new StringBuilder();
				for(String attribute : attributes){
					String rightShift = "    ";
					string.append(attribute + ":<br />");
					for(Object value : instance.getValuesFor(attribute)){
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
			RepeatingView view = new RepeatingView("propertylist");
			for (final String attribute : (Collection<String>) instance.getAttributeNames()) {
				KrassesModel model = new KrassesModel(instance, attribute);				
				Fragment fragment = new Fragment(view.newChildId(), "referenceInput", this);			
				fragment.add((new Label("propertyLabel", attribute)));
				FormComponent c = new TextField<String>("propertyInput",model);
				c.add(new IValidator(){

					public void validate(IValidatable validatable) {
						RBValidator validator = instance.getValidatorFor(attribute);
						try {
							validator.isValid(validatable.getValue());
						} catch (RBInvalidValueException e) {
							ValidationError error = new ValidationError();
							error.setMessage(e.getMessage());
							validatable.error(error);
						}
					}
					
				});
				fragment.add(c);
				view.add(fragment);
			}
			form.add(view);	
		}//End of if(schema==null)


	}//End of Method init
	

	class KrassesModel implements IModel<String>{
		private ResourceTypeInstance instance;
		private String attribute;
		private Integer ticket;
		
		public KrassesModel(ResourceTypeInstance instance, String attribute){
			
			this.instance = instance;
			this.attribute = attribute;
			try {
				if(ticket==null) ticket = instance.generateTicketFor(attribute);
			} catch (Exception e){
				throw new IllegalArgumentException(e);
			}
		}
		
		public String getObject() {
			return (String) instance.getValueFor(attribute, ticket);
		}

		public void setObject(String object) {
			try {
				instance.addValueFor(attribute, object, ticket);
			} catch (Exception e){
				throw new IllegalArgumentException(e);
			}
			
		}

		public void detach() {
			//Do nothing
		}
		
	}
}
