/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

import sun.security.util.Resources_zh_TW;

import de.lichtflut.infra.data.MultiMap;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.RBInvalidAttributeException;
import de.lichtflut.rb.core.schema.model.RBInvalidValueException;
import de.lichtflut.rb.core.schema.model.ResocureTypeInstanceImpl;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
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
		final ResocureTypeInstanceImpl instance = new ResocureTypeInstanceImpl(schema);
		final Form form = new Form("form"){
			@Override
			protected void onSubmit() {
				super.onSubmit();
				Collection<String> attributes = instance.getAttributeNames();
				StringBuilder string = new StringBuilder();
				for(String attribute : attributes){
					String rightShift = "    ";
					string.append(attribute + ":<br />");
					for(String value : instance.getValuesFor(attribute)){
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
			for (String attribute : instance.getAttributeNames()) {
				KrassesModel model = new KrassesModel(instance, attribute);				
				Fragment fragment = new Fragment(view.newChildId(), "referenceInput", this);			
				fragment.add((new Label("propertyLabel", attribute)));
				fragment.add( new TextField<String>("propertyInput",model));
				view.add(fragment);
			}
			form.add(view);	
		}//End of if(schema==null)


	}//End of Method init
	
	
	class KrassesModel implements IModel<String>{
		private ResocureTypeInstanceImpl instance;
		private String attribute;
		private Integer ticket;
		
		public KrassesModel(ResocureTypeInstanceImpl instance, String attribute){
			
			this.instance = instance;
			this.attribute = attribute;
			try {
				if(ticket==null) ticket = instance.generateTicketFor(attribute);
			} catch (Exception e){
				throw new IllegalArgumentException(e);
			}
		}
		
		public String getObject() {
			return instance.getValueFor(attribute, ticket);
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
