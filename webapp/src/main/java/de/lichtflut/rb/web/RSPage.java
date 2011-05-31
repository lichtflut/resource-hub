/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.api.ResourceTypeManagement;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.schema.parser.RSErrorLevel;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.web.genericresource.GenericResourceFormPage;


/**
 * <p>
 *  TODO [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public class RSPage extends RBSuperPage {
	
	private final RepeatingView resourceList =  new RepeatingView("resourcelist");

	
	/**
	 * @param parameters
	 */
	public RSPage(final PageParameters parameters) {
		super("Resource Schema", parameters);
		init(parameters);
	}

	//-------------------------------------------

    @SuppressWarnings({ "unchecked", "serial" })
	private void init(PageParameters parameters) { 
    	final Label schemaErrors = new Label("schemaErrors", new Model<String>(""));
    	schemaErrors.setEscapeModelStrings(false);
    	final Label schemaSuccess = new Label("schemaSuccess", new Model<String>(""));   	
    	
    	final TextArea<String> area = new TextArea<String>("resourceschema", Model.of("Insert your schema here"));
    	area.setOutputMarkupId(true);
    	final DropDownChoice ddc = 
            new DropDownChoice ("formatlist",Model.of(""), new CollectionModel<RSFormat>(Arrays.asList(RSFormat.values())));
    	ddc.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            protected void onUpdate(AjaxRequestTarget target) {
           	 ResourceSchemaManagement rManagement = getRBServiceProvider().getResourceSchemaManagement();
        	 String representation = rManagement.loadSchemaRepresenation((RSFormat) ddc.getModelObject());
        	 if((representation==null || representation.length()==0)) representation = "Insert your schema here";
        	 area.setModelObject(representation);
        	 target.add(area);
            }
        });

    
     
    	Form form = new Form("schemaForm"){
    		@Override
    		protected void onSubmit() {
    			super.onSubmit();
    			schemaSuccess.setVisible(false);
    			//Check RSFormat first
    			RSFormat format = (RSFormat) ddc.getModelObject();
    			if(format==null){
    				schemaErrors.setDefaultModelObject("You have to select at least one format");
    				schemaErrors.setVisible(true);
    				return;
    			}
    			ResourceSchemaManagement rManagement = getRBServiceProvider().getResourceSchemaManagement();
    			rManagement.setFormat(format);
    			String input = area.getModelObject();
    			RSParsingResult result =
    				rManagement.generateAndResolveSchemaModelThrough(input);
    			if(result.isErrorOccured()){
    				schemaErrors.setDefaultModelObject(result.getErrorMessagesAsString("<br />", RSErrorLevel.ALL));
    				schemaErrors.setVisible(true);
    			}else{
    				schemaErrors.setVisible(false);
    				schemaSuccess.setVisible(true);
    				schemaSuccess.setDefaultModelObject("Your given schema has been successfully stored");
    				rManagement.storeOrOverridePropertyDeclaration(result.getPropertyDeclarations());
    				rManagement.storeOrOverrideResourceSchema(result.getResourceSchemas());
    				rManagement.storeSchemaRepresentation(input, format);
    				updateResourceList();
    			}
    		}
    	};
    	add(form);
    	form.add(ddc);
    	form.add(area);
    	form.add(schemaSuccess.setVisible(false));
    	form.add(schemaErrors.setVisible(false));
    	updateResourceList();
		this.add(resourceList);
	}

    
	// -----------------------------------------------------
    
	@SuppressWarnings({ "unchecked", "serial" })
	private void updateResourceList(){
    	resourceList.removeAll();	
    	ResourceSchemaManagement rManagement = getRBServiceProvider().getResourceSchemaManagement();
    	ResourceTypeManagement rTypeManagement = getRBServiceProvider().getResourceTypeManagement();
    	Collection<ResourceSchema> resourceSchemas = rManagement.getAllResourceSchemas();
		if(resourceSchemas==null) resourceSchemas = new ArrayList<ResourceSchema>();
		for (final ResourceSchema resourceSchema : resourceSchemas) {

			Collection<ResourceTypeInstance> instances = rTypeManagement.loadAllResourceTypeInstancesForSchema(resourceSchema);
			
			ArrayList<ResourceTypeInstance> schemaInstances = 
				new ArrayList<ResourceTypeInstance>((instances != null) ? instances : new HashSet<ResourceTypeInstance>());
			
			PageParameters params = new PageParameters();
			params.add("resourceid", resourceSchema.getDescribedResourceID().getQualifiedName().toURI());
			Fragment fragment = new Fragment(resourceList.newChildId(),"listPanel",this);
			fragment.add(new BookmarkablePageLink<GenericResourceFormPage>("link",GenericResourceFormPage.class, params).
					add(new Label("linkLabel",resourceSchema.getDescribedResourceID().getQualifiedName().getSimpleName())));
			
			fragment.add(new ListView("instancelist",schemaInstances){
				@Override
				protected void populateItem(ListItem item) {
					ResourceTypeInstance instance = (ResourceTypeInstance) item.getModelObject();
					PageParameters params = new PageParameters();
					params.add("resourceid", resourceSchema.getDescribedResourceID().getQualifiedName().toURI());
					params.add("instanceid", instance.getQualifiedName().toURI());
					item.add(new BookmarkablePageLink<GenericResourceFormPage>("innerlink",GenericResourceFormPage.class, params).
					add(new Label("innerlinkLabel",instance.toString())));
				}
				
			});
			
			resourceList.add(fragment);
		}
		resourceList.modelChanged();
    }
    

	
}
