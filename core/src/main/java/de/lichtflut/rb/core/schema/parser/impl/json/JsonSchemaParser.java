/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.NamespaceHandle;
import org.arastreju.sge.naming.QualifiedName;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.logging.StopWatch;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionReference;
import de.lichtflut.rb.core.schema.parser.IOConstants;
import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;

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

	private final Logger logger = LoggerFactory.getLogger(JsonSchemaParser.class);
	
	private final Map<String, NamespaceHandle> nsMap = new HashMap<String, NamespaceHandle>();
	
	private final ParsedElements result = new ParsedElements();
	
	private boolean closed; 
	
	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public JsonSchemaParser() {
	}

	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ParsedElements parse(final InputStream in) throws IOException {
		if (closed) {
			throw new IllegalStateException("Parser already closed.");
		} else {
			closed = true;
		}
		final StopWatch sw = new StopWatch();
		final JsonParser p = new JsonFactory().createJsonParser(in);
		
		while (p.nextToken() != null) {
			if (RESOURCE_SCHEMAS.equals(p.getCurrentName())) {
				assertStartArray(p);
				while (p.nextToken() != JsonToken.END_ARRAY) {
					final ResourceSchema schema = readSchema(p, result);
					result.add(schema);
				}
			} else if (PUBLIC_TYPE_DEFINITIONS.equals(p.getCurrentName())) {
				assertStartArray(p);
				while (p.nextToken() != JsonToken.END_ARRAY) {
					final TypeDefinition typeDef = readPublicTypeDef(p, result);
					result.add(typeDef);
				}
			} else if (NAMESPACE_DECLS.equals(p.getCurrentName())) {
				assertStartArray(p);
				while (p.nextToken() != JsonToken.END_ARRAY) {
					readNamespaces(p);
				}
			} else if (p.getCurrentToken() == JsonToken.END_OBJECT ||
					p.getCurrentToken() == JsonToken.START_OBJECT) {
				// ignore
			} else {
 				logger.warn("unkown token : " + p.getCurrentName() + " - " + p.getText());
			}
		}
		p.close();
		logger.debug("parsed {} in {} micros", new Object[] {result, sw.getTime()});
		return result;
	}
	
	// ----------------------------------------------------
	
	private ResourceSchema readSchema(final JsonParser p, final ParsedElements result) throws IOException {
		final ResourceSchemaImpl schema = new ResourceSchemaImpl();
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (FOR_TYPE.equals(field)) {
				schema.setDescribedType(toResourceID(p.getText()));
			} else if (PROPERTY_DECLARATION.equals(field)) {
				final PropertyDeclaration decl = readPropertyDecl(p, result);
				schema.addPropertyDeclaration(decl);
			} else if (LABEL_RULE.equals(field)) {
				final String rule = p.getText();
				try {
					schema.setLabelBuilder(new ExpressionBasedLabelBuilder(rule, nsMap));
				} catch (LabelExpressionParseException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return schema;
	}
	
	private TypeDefinition readPublicTypeDef(final JsonParser p, final ParsedElements result) throws IOException {
		ResourceID id = new SimpleResourceID();
		String name = id.getQualifiedName().getSimpleName();
		Datatype datatype = Datatype.STRING;
		Collection<Constraint> constraints = Collections.emptySet();
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (ID.equals(field)) {
				id = toResourceID(p.getText());
			} else if (NAME.equals(field)) {
				name = p.getText();
			} else if (DATATYPE.equals(field)) {
				 datatype = Datatype.valueOf(p.getText().toUpperCase());
			} else if (CONSTRAINTS.equals(field)) {
				constraints = readConstraints(p);
			}
		}
		final TypeDefinitionImpl def = new TypeDefinitionImpl(id, true);
		def.setName(name);
		def.setDataType(datatype);
		def.setConstraints(constraints);
		return def;
	}
	
	private void readNamespaces(final JsonParser p) throws IOException {
		String uri = null;
		String prefix = null;
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (NAMESPACE.equals(field)) {
				uri = p.getText();
			} else if (PREFIX.equals(field)) {
				prefix = p.getText();
			}
		}
		if (uri != null && prefix != null) {
			register(new NamespaceHandle(uri, prefix));
		} else {
			throw new IllegalStateException("Invalid namespace: uri=" + uri + " prefix=" + prefix);
		}
	}
	
	private PropertyDeclaration readPropertyDecl(final JsonParser p, final ParsedElements result) throws IOException {
		final PropertyDeclarationImpl decl = new PropertyDeclarationImpl();
		int min = 0;
		int max = -1;
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (PROPERTY_TYPE.equals(field)) {
				decl.setPropertyDescriptor(toResourceID(p.getText()));
			} else if (MIN.equals(field)) {
				min = p.getIntValue();
			} else if (MAX.equals(field)) {
				max = p.getIntValue();
			} else if (TYPE_REFERENCE.equals(field)) {
				final ResourceID ref = toResourceID(p.getText());
				decl.setTypeDefinition(new TypeDefinitionReference(ref));
			} else if (TYPE_DEFINITION.equals(field)) {
				decl.setTypeDefinition(readTypeDef(p));
			} else if (FIELD_LABEL.equals(field)) {
				decl.setFieldLabelDefinition(readFieldLabel(p));
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
				def.setDataType(Datatype.valueOf(p.getText().toUpperCase()));
			} else if (CONSTRAINTS.equals(field)) {
				def.setConstraints(readConstraints(p));
			}
		}
		return def;
	}
	
	private FieldLabelDefinition readFieldLabel(final JsonParser p) throws IOException {
		final FieldLabelDefinitionImpl def = new FieldLabelDefinitionImpl();
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (DEFAULT.equals(field)) {
				def.setDefaultLabel(p.getText());
			} else {
				def.setLabel(new Locale(field),p.getText());
			}
		}
		return def;
	}
	
	private Collection<Constraint> readConstraints(final JsonParser p) throws IOException {
		final List<Constraint> result = new ArrayList<Constraint>();
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (LITERAL.equals(field)) {
				result.add(ConstraintBuilder.buildLiteralConstraint(p.getText()));
			} else if (RESOURCE_TYPE.equals(field)){
				result.add(ConstraintBuilder.buildResourceConstraint(toResourceID(p.getText())));
			}
		}
		return result;
	}
	
	// -----------------------------------------------------
	
	private String nextField(final JsonParser p) throws IOException {
		Validate.isTrue(p.getCurrentToken() == JsonToken.FIELD_NAME, "expected fieldname: " + p.getTokenLocation());
		final String field = p.getCurrentName();
		p.nextToken();
		return field;
	}
	
	private void assertStartArray(final JsonParser p) throws IOException {
		Validate.isTrue(p.nextToken() == JsonToken.START_ARRAY);
	}
	
	// ----------------------------------------------------
	
	public ResourceID toResourceID(final String name) {
		if (QualifiedName.isUri(name)) {
			logger.debug("found uri " + name);
			return new SimpleResourceID(name); 
		} else if (!QualifiedName.isQname(name)) {
			throw new IllegalArgumentException("Name is neither URI nor QName: " + name);
		}
		final String prefix = QualifiedName.getPrefix(name);
		final String simpleName = QualifiedName.getSimpleName(name);
		if (nsMap.containsKey(prefix)) {
			logger.debug("found registered prefix " + nsMap.get(prefix));
			return new SimpleResourceID(nsMap.get(prefix).getUri(), simpleName);
		} else {
			final NamespaceHandle handle = new NamespaceHandle(null, prefix);
			nsMap.put(prefix, handle);
			logger.debug("found unknown prefix " + prefix);
			return new SimpleResourceID(handle.getUri(), simpleName);	
		}
	}
	
	// -- NAMESPACES --------------------------------------
	
	/**
	 * @param namespaceHandle
	 */
	private void register(final NamespaceHandle namespace) {
		logger.debug("registered namespace: " + namespace);
		final NamespaceHandle registered = nsMap.get(namespace.getPrefix());
		if (registered != null) {
			registered.setUri(namespace.getUri());
		} else {
			nsMap.put(namespace.getPrefix(), namespace);
		}
	}

}
