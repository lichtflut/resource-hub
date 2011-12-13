/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.json;


import static org.arastreju.sge.SNOPS.uri;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.arastreju.sge.io.NamespaceMap;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.parser.IOConstants;
import de.lichtflut.rb.core.schema.parser.OutputElements;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaWriter;

/**
 * <p>
 *  Schema exporter to JSON format.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class JsonSchemaWriter implements ResourceSchemaWriter, IOConstants {

	
    /** 
	 * {@inheritDoc}
	 */
	@Override
	public void write(final OutputStream out, final OutputElements elements) throws IOException {
		final JsonGenerator g = new JsonFactory().createJsonGenerator(out).useDefaultPrettyPrinter();
		g.writeStartObject();
		write(g, elements.getNamespaceMap());
		writeSchemas(g, elements.getSchemas());
		writeTypeDefs(g, elements.getTypeDefs());
		g.close(); 
	}
	
	// -----------------------------------------------------

	/**
	 * @param g The generator.
	 * @param schemas
	 * @throws IOException
	 * @throws JsonGenerationException
	 */
	protected void writeSchemas(final JsonGenerator g, final List<ResourceSchema> schemas)
			throws IOException, JsonGenerationException {
		g.writeArrayFieldStart(RESOURCE_SCHEMAS);
		for (ResourceSchema schema : schemas) {
			g.writeStartObject();
			writeSchema(g, schema);
			g.writeEndObject();
		}
		g.writeEndArray();
	}
	
	/**
	 * @param g The generator.
	 * @param typeDefinitions
	 * @throws IOException
	 * @throws JsonGenerationException
	 */
	public void writeTypeDefs(final JsonGenerator g,  final List<TypeDefinition> typeDefinitions) throws IOException {
		g.writeArrayFieldStart(PUBLIC_TYPE_DEFINITIONS);
		for (TypeDefinition def : typeDefinitions) {
			g.writeStartObject();
			writePublicTypeDef(g, def);
			g.writeEndObject();
		}
		g.writeEndArray();
	}
	
	/**
	 * @param g The generator.
	 * @param map The namespace map.
	 * @throws IOException
	 * @throws JsonGenerationException
	 */
	public void write(final JsonGenerator g, final NamespaceMap map) throws IOException {
		g.writeArrayFieldStart(NAMESPACE_DECLS);
		for (String prefix : map.getPrefixes()) {
			g.writeStartObject();
			g.writeStringField(NAMESPACE, map.getNamespace(prefix).getUri());
			g.writeStringField(PREFIX, prefix);
			g.writeEndObject();
		}
		g.writeEndArray();
	}
	
	// -----------------------------------------------------
	
	private void writeSchema(final JsonGenerator g, ResourceSchema schema) throws IOException {
		g.writeStringField(FOR_TYPE, schema.getDescribedType().getQualifiedName().toURI());
		if (schema.getLabelBuilder().getExpression() != null) {
			g.writeStringField(LABEL_RULE, schema.getLabelBuilder().getExpression());	
		}
		for(PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			g.writeObjectFieldStart(PROPERTY_DECLARATION);
			g.writeStringField(PROPERTY_TYPE, uri(decl.getPropertyDescriptor()));
			g.writeNumberField(MIN, decl.getCardinality().getMinOccurs());
			writeFieldLabelDef(g, decl.getFieldLabelDefinition());
			if (!decl.getCardinality().isUnbound()) {
				g.writeNumberField(MAX, decl.getCardinality().getMaxOccurs());
			}
			if (decl.getTypeDefinition().isPublicTypeDef()) {
				g.writeStringField(TYPE_REFERENCE, uri(decl.getTypeDefinition().getID()));
			} else {
				writeImplicitTypeDef(g, decl.getTypeDefinition());
			}
			g.writeEndObject();
		}
	}

	private void writePublicTypeDef(final JsonGenerator g, final TypeDefinition def) throws JsonGenerationException, IOException {
		if (def.isPublicTypeDef()) {
			g.writeStringField(ID, uri(def.getID()));
			g.writeStringField(NAME, def.getName());
		}
		g.writeStringField(DATATYPE, def.getElementaryDataType().name().toLowerCase());
		if (!def.getConstraints().isEmpty()) {
			writeConstraints(g, def.getConstraints());	
		}
	}
	
	private void writeImplicitTypeDef(final JsonGenerator g, final TypeDefinition def) throws JsonGenerationException, IOException {
		g.writeObjectFieldStart(TYPE_DEFINITION);
		g.writeStringField(DATATYPE, def.getElementaryDataType().name().toLowerCase());
		if (!def.getConstraints().isEmpty()) {
			writeConstraints(g, def.getConstraints());	
		}
		g.writeEndObject();
	}
	
	private void writeFieldLabelDef(final JsonGenerator g, final FieldLabelDefinition def) throws JsonGenerationException, IOException {
		g.writeObjectFieldStart(FIELD_LABEL);
		if (def.getDefaultLabel() != null) {
			g.writeStringField(DEFAULT, def.getDefaultLabel());
		}
		for(Locale locale : def.getSupportedLocales()) {
			g.writeStringField(locale.toString(), def.getLabel(locale));
		}
		g.writeEndObject();
	}
	
	private void writeConstraints(final JsonGenerator g, final Collection<Constraint> constraints) throws JsonGenerationException, IOException {
		g.writeObjectFieldStart(CONSTRAINTS);
		for (Constraint constraint : constraints) {
			if (constraint.isLiteralConstraint()) {
				g.writeStringField(LITERAL, constraint.getLiteralConstraint());
			} else {
				g.writeStringField(RESOURCE_TYPE, uri(constraint.getResourceTypeConstraint()));
			}
		}
		g.writeEndObject();
	}
	
}