/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.genericresource;

import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance.MetaDataKeys;
import de.lichtflut.rb.core.spi.RBServiceProvider;

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

	private RBServiceProvider provider;
	
	//Constructors
	
	/**
	 * 
	 */
	public GenericResourceFormPanel(String id, ResourceSchema schema, RBServiceProvider provider, ResourceTypeInstance instance) {
		super(id);
		this.provider=provider;
		init(schema, instance);
	}

	
	// -----------------------------------------------------
	
	private void init(ResourceSchema schema, ResourceTypeInstance in){
		
		final ResourceTypeInstance instance = (in==null ? schema.generateTypeInstance() : in);
		final Form form = new Form("form"){
			@Override
			protected void onSubmit() {
				super.onSubmit();
				if(provider.getResourceTypeManagement().createOrUpdateRTInstance(instance)){
					info("This instance has been successfully updated/created");
				}else{
					error("Somethin went wrong, instance could'nt be created/updated");
				}
				//Here should be a redirect or something like that
			}
		};
		form.setOutputMarkupId(true);
		this.add(form);
		form.add(new FeedbackPanel("feedback").setEscapeModelStrings(false));
		
		if(schema!=null){
			
			RepeatingView view = new RepeatingView("propertylist");
			for (final String attribute : (Collection<String>) instance.getAttributeNames()) {
				boolean required=true;
				int minimum_cnt = (Integer )instance.geMetaInfoFor(attribute,MetaDataKeys.MIN);
				if(minimum_cnt==0){
					minimum_cnt=1;
					required=false;	
				}
				//Get predefined tickets, if there are some
				Collection<Integer> tickets = instance.getTicketsFor(attribute);
				for(int cnt = 0; cnt< minimum_cnt; cnt++){
					GenericResourceModel model;
					if(cnt>= (tickets.size()-1)){
						model =  new GenericResourceModel(instance, attribute);
					}else{
						model =  new GenericResourceModel(instance, attribute,(Integer) tickets.toArray()[cnt]);
					}			
					view.add(buildItem(instance,model, attribute, view, required, (cnt+1)==minimum_cnt));
				}
			}
			form.add(view);	
		}//End of if(schema==null)

	}//End of Method init
	
	
	
	private Component buildItem(final ResourceTypeInstance instance,final GenericResourceModel model, final String attribute, final RepeatingView view, boolean required, boolean expendable){
		Fragment fragment = new Fragment(view.newChildId(), "referenceInput", this);			
		fragment.add((new Label("propertyLabel", instance.getSimpleAttributeName(attribute) + 
				(required ? " (*)" : ""))));
		FormComponent c = new TextField<String>("propertyInput",model);
		c.setRequired(required);
		//Add a validator
		c.add(new GenericResourceValidator(instance.getValidatorFor(attribute)));
		fragment.add(c);
		AjaxButton button = new AjaxButton("addField"){

			@Override
			protected void onError(AjaxRequestTarget target,Form<?> form) {
				//Do nothing							
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target,Form<?> form) {
			      Component item = buildItem(instance,new GenericResourceModel(instance, attribute), attribute, view, false, true);
			      // first execute javascript which creates a placeholder tag in markup for this item
			      target.prependJavascript(
			        String.format(
			        "var item=document.createElement('%s');item.id='%s';"+
			        "Wicket.$('%s').appendChild(item);",
			        "tr", item.getMarkupId(), item.getMarkupId()));
			      	view.add(item);
			        target.addComponent(item);
			}
		};
		button.add(new Label("linkLabel","(+)"));
		button.setVisible((expendable &&
				((Integer) instance.geMetaInfoFor(attribute, MetaDataKeys.MAX)) > 
				((Integer) instance.geMetaInfoFor(attribute, MetaDataKeys.CURRENT))));
		fragment.add(button);
		return fragment;
	}
	
}
