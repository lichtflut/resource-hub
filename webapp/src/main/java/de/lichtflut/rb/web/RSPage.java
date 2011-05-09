/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSErrorLevel;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;


/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public class RSPage extends RBSuperPage {

	private  final ResourceSchemaManagement rManagement = getServiceProvider().getResourceSchemaManagement();
	
	private final RepeatingView resourceList =  new RepeatingView("resourcelist");

	
	/**
	 * @param parameters
	 */
	public RSPage(final PageParameters parameters) {
		super(parameters);
		init(parameters);
	}

	//-------------------------------------------

    @SuppressWarnings({ "unchecked", "serial" })
	private void init(PageParameters parameters) { 
    	final Label schemaErrors = new Label("schemaErrors", new Model<String>(""));
    	schemaErrors.setEscapeModelStrings(false);
    	final Label schemaSuccess = new Label("schemaSuccess", new Model<String>(""));   	
    	
    	final TextArea<String> area = new TextArea<String>("resourceschema", Model.of("Insert your schema here"));
    	final DropDownChoice ddc = 
            new DropDownChoice ("formatlist",Model.of(""), new CollectionModel<RSFormat>(Arrays.asList(RSFormat.values())));
        
        
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
    				updateResourceList();
    			}
    		}
    	};
    	add(form);
    	form.add(ddc);
    	form.add(area);
    	form.add(schemaSuccess.setVisible(false));
    	form.add(schemaErrors.setVisible(false));
		this.add(resourceList);
		
		updateResourceList();
	}

    
	// -----------------------------------------------------
    
	private void updateResourceList(){
    	resourceList.removeAll();	
    	Collection<ResourceSchema> resourceSchemas = rManagement.getAllResourceSchemas();
		if(resourceSchemas==null) resourceSchemas = new ArrayList<ResourceSchema>();
		for (ResourceSchema resourceSchema : resourceSchemas) {
			PageParameters params = new PageParameters();
			params.add("resourceid", resourceSchema.getDescribedResourceID().getQualifiedName().toURI());
			CharSequence url = urlFor(GenericResourceFormPage.class, params);
			resourceList.add(new ExternalLink(resourceList.newChildId(), url.toString(),resourceSchema.getDescribedResourceID().getQualifiedName().getSimpleName()).setPopupSettings(null));
		}
		resourceList.modelChanged();
    }
    
	
}
