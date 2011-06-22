/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.parser.RSErrorLevel;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;



/**
 * <p>
 *  TODO: [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Nils Bleisch
 */

public abstract class SchemaSubmitPanel extends CKComponent {

	private static final long serialVersionUID = 5141797984277420136L;

	//-------------------------------------------

	/**
	 *@param id /
	 */

	public SchemaSubmitPanel(final String id) {
		super(id);
		this.buildComponent();
	}

	//-------------------------------------------



	// -----------------------------------------------------

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	protected void initComponent(final CKValueWrapperModel model) {

    	final TextArea<String> area = new TextArea<String>("resourceschema", Model.of("Insert your schema here"));
    	area.setOutputMarkupId(true);
    	@SuppressWarnings("rawtypes")
		final DropDownChoice ddc = new DropDownChoice(
    			"formatlist",
    			Model.of(""),
    			new CollectionModel<RSFormat>(Arrays.asList(RSFormat.values())));
    	ddc.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            protected void onUpdate(final AjaxRequestTarget target) {
           	 ResourceSchemaManagement rManagement = getServiceProvider().getResourceSchemaManagement();
        	 String representation = rManagement.loadSchemaRepresenation((RSFormat) ddc.getModelObject());
        	 if((representation==null || representation.length()==0)) {
				representation = "Insert your schema here";
			}
        	 area.setModelObject(representation);
        	 target.add(area);
            }
        });


    	@SuppressWarnings("rawtypes")
		Form form = new Form("schemaForm"){
    		@Override
    		protected void onSubmit() {
    			super.onSubmit();
    			//Check RSFormat first
    			RSFormat format = (RSFormat) ddc.getModelObject();
    			if(format==null){
    				error("You have to select at least one format");
    				return;
    			}
    			ResourceSchemaManagement rManagement = getServiceProvider().getResourceSchemaManagement();
    			rManagement.setFormat(format);
    			String input = area.getModelObject();
    			RSParsingResult result =
    				rManagement.generateAndResolveSchemaModelThrough(input);
    			if(result.isErrorOccured()){
    				error(result.getErrorMessagesAsString("\n", RSErrorLevel.ALL));

    			}else{

    				rManagement.storeOrOverridePropertyDeclaration(result.getPropertyDeclarations());
    				rManagement.storeOrOverrideResourceSchema(result.getResourceSchemas());
    				rManagement.storeSchemaRepresentation(input, format);
    				info("Your given schema has been successfully stored");
    			}
    		}
    	};
    	add(form);
    	form.add(ddc);
    	form.add(new FeedbackPanel("feedback").setEscapeModelStrings(false));
    	form.add(area);
	}

    // -----------------------------------------------------

	/**
	 * @param view /
	 * @return NotYetImplementedException
	 */
	public CKComponent setViewMode(final ViewMode view) {
		throw new NotYetImplementedException();
	}

}
