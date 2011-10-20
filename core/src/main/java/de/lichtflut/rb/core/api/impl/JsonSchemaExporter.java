/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;


import java.io.IOException;
import java.io.OutputStream;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import de.lichtflut.rb.core.api.SchemaExporter;
import de.lichtflut.rb.core.api.SchemaManager;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class JsonSchemaExporter extends AbstractSchemaExporter implements SchemaExporter {

	/**
	 * Constructor.
	 * @param manager The schema manager.
	 */
	public JsonSchemaExporter(final SchemaManager manager) {
		super(manager);
	}
	
	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void write(OutputStream out, ResourceSchema... schemas) throws IOException {
		final JsonGenerator g = new JsonFactory().createJsonGenerator(out);
		
		g.useDefaultPrettyPrinter();
		
		g.writeStartObject();
		g.writeObjectFieldStart("schemas");
		
		for (ResourceSchema schema : schemas) {
			g.writeStringField("for-type", schema.getDescribedType().getQualifiedName().toURI());
			for(PropertyDeclaration decl : schema.getPropertyDeclarations()) {
				g.writeObjectFieldStart("property-declaration");
				g.writeStringField("property-type", decl.getPropertyType().getQualifiedName().toURI());
				g.writeEndObject();
			}
			g.writeEndObject();
		}
		g.writeEndObject();
		g.close(); 
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void write(OutputStream out, TypeDefinition... typeDefinitions) throws IOException {
	}
	

}
