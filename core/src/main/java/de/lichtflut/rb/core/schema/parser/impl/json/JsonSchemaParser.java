/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.schema.parser.IOConstants;
import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;
import de.lichtflut.rb.core.schema.persistence.TypeDefinitionResolver;

/**
 * <p>
 *  Schema importer from JSON formart.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class JsonSchemaParser implements ResourceSchemaParser, IOConstants {

	@SuppressWarnings("unused")
	private final TypeDefinitionResolver resolver;
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param resolver
	 */
	public JsonSchemaParser(final TypeDefinitionResolver resolver) {
		this.resolver = resolver;
	}

	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ParsedElements parse(InputStream in) throws IOException {
		final JsonParser p = new JsonFactory().createJsonParser(in);
		
		while (p.nextToken() != null) {
			if (RESOURCE_SCHEMAS.equals(p.getCurrentName())) {
				assertStartArray(p);
				while (p.nextToken() != JsonToken.END_ARRAY) {
					ResourceSchema schema = readSchema(p);
					System.out.println(schema);
				}
			} else if (PUBLIC_TYPE_DEFINITIONS.equals(p.getCurrentName())) {
				assertStartArray(p);
				while (p.nextToken() != JsonToken.END_ARRAY) {
					TypeDefinition typeDef = readPublicTypeDef(p);
					System.out.println(typeDef);
				}
			} else {
 				System.err.println("unkonw token: " + p.getCurrentName() + " - " + p.getText());
			}
		}
		p.close();
		
		return new ParsedElements();
	}

	// -----------------------------------------------------
	
	private ResourceSchema readSchema(final JsonParser p) throws IOException {
		final ResourceSchemaImpl schema = new ResourceSchemaImpl();
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (FOR_TYPE.equals(field)) {
				schema.setDescribedType(new SimpleResourceID(p.getText()));
			} else if (PROPERTY_DECLARATION.equals(field)) {
				final PropertyDeclaration decl = readDecl(p);
				schema.addPropertyDeclaration(decl);
			} 
		}
		return schema;
	}
	
	private TypeDefinition readPublicTypeDef(final JsonParser p) throws IOException {
		ResourceID id = new SimpleResourceID();
		String name = id.getName();
		ElementaryDataType datatype = ElementaryDataType.STRING;
		Collection<Constraint> constraints = Collections.emptySet();
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (ID.equals(field)) {
				id = new SimpleResourceID(p.getText());
			} else if (NAME.equals(field)) {
				name = p.getText();
			} else if (DATATYPE.equals(field)) {
				 datatype = ElementaryDataType.valueOf(p.getText().toUpperCase());
			} else if (CONSTRAINTS.equals(field)) {
				constraints = readConstraints(p);
			}
		}
		final TypeDefinitionImpl def = new TypeDefinitionImpl(id, true);
		def.setName(name);
		def.setElementaryDataType(datatype);
		def.setConstraints(constraints);
		return def;
	}
	
	private PropertyDeclaration readDecl(final JsonParser p) throws IOException{
		final PropertyDeclarationImpl decl = new PropertyDeclarationImpl();
		int min = 0;
		int max = -1;
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (PROPERTY_TYPE.equals(field)) {
				decl.setPropertyType(new SimpleResourceID(p.getText()));
			} else if (MIN.equals(field)) {
				min = p.getIntValue();
			} else if (MAX.equals(field)) {
				max = p.getIntValue();
			} else if (TYPE_REFERENCE.equals(field)) {
				
			} else if (TYPE_DEFINITION.equals(field)) {
				decl.setTypeDefinition(readTypeDef(p));
			}
		}
		decl.setCardinality(CardinalityBuilder.between(min, max));
		return decl;
	}
	
	private TypeDefinition readTypeDef(final JsonParser p) throws IOException {
		final TypeDefinitionImpl def = new TypeDefinitionImpl();
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (DATATYPE.equals(field)) {
				def.setElementaryDataType(ElementaryDataType.valueOf(p.getText().toUpperCase()));
			} else if (CONSTRAINTS.equals(field)) {
				def.setConstraints(readConstraints(p));
			}
		}
		return def;
	}
	
	private Collection<Constraint> readConstraints(final JsonParser p) throws IOException {
		final List<Constraint> result = new ArrayList<Constraint>();
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (LITERAL.equals(field)) {
				result.add(ConstraintBuilder.buildConstraint(p.getText()));
			} else if (RESOURCE_TYPE.equals(field)){
				result.add(ConstraintBuilder.buildConstraint(new SimpleResourceID(p.getText())));
			}
		}
		return result;
	}
	
	// -----------------------------------------------------
	
	private String nextField(final JsonParser p) throws IOException {
		Validate.isTrue(p.getCurrentToken() == JsonToken.FIELD_NAME);
		final String field = p.getCurrentName();
		p.nextToken();
		System.out.println("  " + field + " : " + p.getText());
		return field;
	}
	
	private void assertStartArray(final JsonParser p) throws IOException {
		Validate.isTrue(p.nextToken() == JsonToken.START_ARRAY);
	}

}
