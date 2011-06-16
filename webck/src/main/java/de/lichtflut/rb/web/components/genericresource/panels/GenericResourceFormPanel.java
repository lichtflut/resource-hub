/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.genericresource.panels;

import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.arastreju.sge.model.ElementaryDataType;
import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.schema.model.RBEntityFactory;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.RBEntityFactory.MetaDataKeys;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.behaviors.DatePickerBehavior;
import de.lichtflut.rb.web.components.genericresource.GenericResourceComponent;
import de.lichtflut.rb.web.components.genericresource.fields.search.SearchBar;
import de.lichtflut.rb.web.components.validators.GenericResourceValidator;
import de.lichtflut.rb.web.models.GenericResourceModel;

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
public abstract class GenericResourceFormPanel extends Panel implements GenericResourceComponent{
	
	//Constructors
	
	/**
	 * 
	 */
	public GenericResourceFormPanel(String id, ResourceSchema schema, RBEntityFactory instance) {
		super(id);
		init(schema, instance);
	}

	
	// -----------------------------------------------------
	
	private void init(ResourceSchema schema, RBEntityFactory in){
		
		final RBEntityFactory instance = (in==null ? schema.generateRBEntity() : in);
		final Form form = new Form("form"){
			@Override
			protected void onSubmit() {
				super.onSubmit();
				if(getServiceProvider().getRBEntityManagement().createOrUpdateRTInstance(instance)){
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
				int minimum_cnt = (Integer )instance.getMetaInfoFor(attribute,MetaDataKeys.MIN);
				if(minimum_cnt==0){
					minimum_cnt=1;
					required=false;	
				}
				//Get predefined tickets, if there are some
				Collection<Integer> tickets = instance.getTicketsFor(attribute);
				for(int cnt = 0; cnt< minimum_cnt; cnt++){
					GenericResourceModel model;
					try{
						if(cnt>= (tickets.size())){
							model =  new GenericResourceModel(instance, attribute);
						}else{
							model =  new GenericResourceModel(instance, attribute,(Integer) tickets.toArray()[cnt]);
						}
					}catch(IllegalArgumentException any){
						//If something went wrong with model creation, skip this field
						continue ;
					}
					view.add(buildItem(instance,model, attribute, view, required, (cnt+1)==minimum_cnt));
				}
			}
			form.add(view);	
		}//End of if(schema==null)

	}//End of Method init
	
	// -----------------------------------------------------
	
	
	private Component buildItem(final RBEntityFactory instance,final GenericResourceModel model, final String attribute, final RepeatingView view, boolean required, boolean expendable){
		Fragment fragment = new Fragment(view.newChildId(), "referenceInput", this);			
		fragment.add((new Label("propertyLabel", instance.getSimpleAttributeName(attribute) + 
				(required ? " (*)" : ""))));
		//Decide which input-field should be used
		Fragment f=null;
		final GenericResourceComponent rComponent = this;
		switch((ElementaryDataType)instance.getMetaInfoFor(attribute, MetaDataKeys.TYPE)){
			case RESOURCE : {
				f = new Fragment("propertyInput", "resourceInput", this);
				f.add(new SearchBar("searchbar"){
					public void onSearchSubmit(RBEntityFactory instance) {
						
						model.setObject(instance);
					}

					// ----------------------------------------
					
					public RBServiceProvider getServiceProvider() {
						return rComponent.getServiceProvider();
					}	
				});
				break;
			}
			case BOOLEAN : {
				f = new Fragment("propertyInput", "booleanInput", this);
				f.add(new CheckBox("input",model)); break;
			}
			case DATE : {
				
				f = new Fragment("propertyInput","textInput", this);
				TextField field = new TextField("input",model);
				field.add(new DatePickerBehavior());
				f.add(field.add(new GenericResourceValidator(instance.getValidatorFor(attribute))).setRequired(required)); break;
			}
			default: {
				f = new Fragment("propertyInput","textInput", this);
				f.add(new TextField("input",model).
						add(new GenericResourceValidator(instance.getValidatorFor(attribute))).setRequired(required)); break;
			}
		}
		fragment.add(f);
		AjaxButton button = new AjaxButton("addField"){

			@Override
			protected void onError(AjaxRequestTarget target,Form<?> form) {
				//Do nothing							
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target,Form<?> form) {
				//This does not really work, so do nothing
				//TODO: Fix it
			    /*  Component item = buildItem(instance,new GenericResourceModel(instance, attribute), attribute, view, false, true);
			      // first execute javascript which creates a placeholder tag in markup for this item
			      target.prependJavascript(
			        String.format(
			        "var item=document.createElement('%s');item.id='%s';"+
			        "Wicket.$('%s').appendChild(item);",
			        "tr", item.getMarkupId(), item.getMarkupId()));
			      	view.add(item);
			        target.addComponent(item); */
			}
		};
		button.add(new Label("linkLabel","(+)"));
		button.setVisible((expendable &&
				((Integer) instance.getMetaInfoFor(attribute, MetaDataKeys.MAX)) > 
				((Integer) instance.getMetaInfoFor(attribute, MetaDataKeys.CURRENT))));
		fragment.add(button);
		return fragment;
	}	
	
	// -----------------------------------------------------
	
	public GenericResourceComponent setViewMode(ViewMode view){
		throw new NotYetImplementedException();
		
	}
	
}
