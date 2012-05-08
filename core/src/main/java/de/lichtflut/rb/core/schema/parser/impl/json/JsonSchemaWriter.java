/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.json;


import static org.arastreju.sge.SNOPS.uri;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.naming.QualifiedName;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
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
		writePublicConstraints(g, elements.getConstraints());
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
	 * @param constraints
	 * @throws IOException
	 * @throws JsonGenerationException
	 */
	public void writePublicConstraints(final JsonGenerator g,  final List<Constraint> constraints) throws IOException {
		g.writeArrayFieldStart(PUBLIC_CONSTRAINTS);
		for (Constraint constr : constraints) {
			g.writeStartObject();
			writePublicConstraint(g, constr);
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
			g.writeStringField(CARDINALITY, buildCardinalityString(decl.getCardinality()));
			writeFieldLabelDef(g, decl.getFieldLabelDefinition());
			if (decl.getConstraint().isPublicConstraint()) {
				g.writeStringField(CONSTRAINT_REFERENCE, uri(decl.getConstraint().getID()));
			} else {
				if(decl.getConstraint().isResourceReference()){
					g.writeStringField(RESOURCE_CONSTRAINT, uri(decl.getConstraint().getResourceConstraint()));
				} else{
					g.writeStringField(LITERAL_CONSTRAINT, decl.getConstraint().getLiteralConstraint());
				}
			}
			g.writeStringField(DATATYPE, decl.getDatatype().name());
			g.writeEndObject();
		}
	}

	private void writePublicConstraint(final JsonGenerator g, final Constraint constr) throws JsonGenerationException, IOException {
		if (constr.isPublicConstraint()) {
			g.writeStringField(ID, uri(constr.getID()));
			g.writeStringField(NAME, constr.getName());
			g.writeStringField(APPLICABLE_DATATYPES, prepareApplicableDatatypes(constr.getApplicableDatatypes()));
		}
		writeConstraints(g, constr);	
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
	
	private void writeConstraints(final JsonGenerator g, final Constraint constraint) throws JsonGenerationException, IOException {
			if (constraint.isResourceReference()) {
				g.writeStringField(RESOURCE_CONSTRAINT, uri(constraint.getResourceConstraint()));
			} else {
				if(QualifiedName.isUri(constraint.getLiteralConstraint())){
					g.writeStringField(CONSTRAINT_REFERENCE, constraint.getLiteralConstraint());
				}else{
					g.writeStringField(LITERAL_CONSTRAINT, constraint.getLiteralConstraint().toString());
				}
			}
	}
	
	private String prepareApplicableDatatypes(List<Datatype> applicableDatatypes) {
		StringBuilder sb = new StringBuilder("");
		for (Datatype datatype : applicableDatatypes) {
			sb.append(datatype.name() + ", ");
		}
		sb.substring(0, (sb.length()-2));
		return sb.toString().trim();
	}
	
	private String buildCardinalityString(Cardinality cardinality) {
		StringBuilder sb = new StringBuilder("[");
		if(cardinality.getMinOccurs() == 0){
			sb.append("n..");
		}else{
			sb.append(cardinality.getMinOccurs() + " ..");
		}
		if(cardinality.isUnbound()){
			sb.append("n]");
		}else{
			sb.append(cardinality.getMaxOccurs() + "]");
		}
		return sb.toString();
	}
	
}
