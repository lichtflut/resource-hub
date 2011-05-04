/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.Collection;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;

import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.parser.RSFormat;
import de.lichtflut.rb.core.schema.parser.RSParsingResult;
import de.lichtflut.rb.core.spi.RBServiceProviderFactory;
import de.lichtflut.rb.core.spi.impl.DefaultRBServiceProvider;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RSPage extends WebPage {

	/**
	 * @param parameters
	 */
	public RSPage(final PageParameters parameters) {
		super(parameters);
		add(new SchemaForm("schemaForm"));
	}

	

    /**
     * A form that allows a user to add a resource schema.
     */
    @SuppressWarnings("serial")
	public final class SchemaForm extends Form<ValueMap> {
        public SchemaForm(final String id) {
            // Construct form with no validation listener
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));

            //to make unit tests happy
            setMarkupId("schemaForm");
            add(new TextArea<String>("resourceschema").setType(String.class));
        }
        
        /**
         * Show the resulting valid edit
         */
        @Override
        public final void onSubmit() {
            ValueMap values = getModelObject();       
            String schema = (String) values.get("resourceschema");
            ResourceSchemaManagement sManagement = RBServiceProviderFactory.getDefaultServiceProvider().getResourceSchemaManagement();
            sManagement.setFormat(RSFormat.OSF);
            RSParsingResult result = sManagement.generateAndResolveSchemaModelThrough(schema);
            if(result.isErrorOccured()){
            	values.put("text", "An error is occured " + result.getErrorMessagesAsString());
            }else{
            	Collection<ResourceSchema> schemas = result.getResourceSchemas();
            	StringBuffer sBuffer = new StringBuffer();
            	for (ResourceSchema resourceSchema : schemas) {
					sBuffer.append(schema.toString() + "\n");
				}
            	values.put("resourceschema", sBuffer.toString());
            }
            
        }
    }
	

	
}
