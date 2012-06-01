/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser.impl.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.NamespaceHandle;
import org.arastreju.sge.naming.QualifiedName;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.actors.threadpool.Arrays;
import de.lichtflut.infra.logging.StopWatch;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.model.impl.LiteralConstraint;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ReferenceConstraint;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.parser.IOConstants;
import de.lichtflut.rb.core.schema.parser.ParsedElements;
import de.lichtflut.rb.core.schema.parser.ResourceSchemaParser;

/**
 * <p>
 *  Schema importer from JSON format.
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
			} else if (PUBLIC_CONSTRAINTS.equals(p.getCurrentName())) {
				assertStartArray(p);
				while (p.nextToken() != JsonToken.END_ARRAY) {
					readPublicConstraints(p, result);
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
			}else {
 				logger.warn("unkown token : " + p.getCurrentName() + " - " + p.getText());
			}
		}
		return schema;
	}
	
	private void readPublicConstraints(final JsonParser p, final ParsedElements result) throws IOException {
		ResourceID id = new SimpleResourceID();
		String name = "";
		List<Datatype> datatypes = new ArrayList<Datatype>();
		Constraint referenceHolder;
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (ID.equals(field)) {
				id = toResourceID(p.getText());
			} else if (NAME.equals(field)) {
				name = p.getText();
			} else if(APPLICABLE_DATATYPES.equals(field)){
					datatypes.addAll(extractDatatypes(p));
			} else if (RESOURCE_CONSTRAINT.equals(field)) {
				referenceHolder = getConstraintFromString(p, field);
				ReferenceConstraint refConstr = new ReferenceConstraint(new SNResource(id.getQualifiedName()));
//				refConstr.isLiteral(false);
				refConstr.setName(name);
				refConstr.setReference(referenceHolder.getReference());
				refConstr.setIsPublic(true);
				result.add(refConstr);
			} else if (LITERAL_CONSTRAINT.equals(field)) {
				referenceHolder = getConstraintFromString(p, field);
				ReferenceConstraint refConstr = new ReferenceConstraint(new SNResource(id.getQualifiedName()));
//				refConstr.isLiteral(true);
				refConstr.setDatatypes(datatypes);
				refConstr.setName(name);
				refConstr.setIsPublic(true);
//				refConstr.holdsReference(false);
				refConstr.setIsPublic(true);
				refConstr.setLiteralConstraint(referenceHolder.getLiteralConstraint());
				result.add(refConstr);
			} else {
 				logger.warn("unkown token : " + p.getCurrentName() + " - " + p.getText());
			}
		}
	}
	
	private Collection<Datatype> extractDatatypes(JsonParser p) throws JsonParseException, IOException {
		List<Datatype> list = new ArrayList<Datatype>();
			for (Object string : Arrays.asList(p.getText().split(","))) {
				list.add(Datatype.valueOf(((String) string).trim().toUpperCase()));
			}
		return list;
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
		Cardinality cardinality = CardinalityBuilder.hasOptionalOneToMany();
		while (p.nextToken() != JsonToken.END_OBJECT) {
			final String field = nextField(p);
			if (PROPERTY_TYPE.equals(field)) {
				decl.setPropertyDescriptor(toResourceID(p.getText()));
			} else if (CARDINALITY.equals(field)) {
				cardinality = CardinalityBuilder.extractFromString(p.getText());
			} else if (DATATYPE.equals(field)) {
				decl.setDatatype(Datatype.valueOf(p.getText().toUpperCase()));
			} else if (RESOURCE_CONSTRAINT.equals(field)) {
				decl.setConstraint(getConstraintFromString(p, field));
			} else if (LITERAL_CONSTRAINT.equals(field)) {
				decl.setConstraint(getConstraintFromString(p, field));
			} else if (CONSTRAINT_REFERENCE.equals(field)) {
				decl.setConstraint(getConstraintFromString(p, field));
			} else if (FIELD_LABEL.equals(field)) {
				decl.setFieldLabelDefinition(readFieldLabel(p));
			}else {
 				logger.warn("unkown token : " + p.getCurrentName() + " - " + p.getText());
			}
		}
		decl.setCardinality(cardinality);
		return decl;
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
	
	private Constraint getConstraintFromString(final JsonParser p, final String field) throws IOException, JsonParseException {
		Constraint c = null;
		if (LITERAL_CONSTRAINT.equals(field)) {
			LiteralConstraint constr = new LiteralConstraint(new SNResource());
			constr.setLiteralPattern(p.getText());
			c = constr;
		} else if (RESOURCE_CONSTRAINT.equals(field)){
			ReferenceConstraint ref = new ReferenceConstraint(new SNResource(toResourceID(p.getText()).getQualifiedName()));
			ref.setReference(toResourceID(p.getText()));
			c = ref;
		}else if (CONSTRAINT_REFERENCE.equals(field)){
			ReferenceConstraint ref = new ReferenceConstraint(new SNResource(toResourceID(p.getText()).getQualifiedName()));
			ref.setIsPublic(true);
			c = ref;
		}
		return c;
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
	
	/**
	 * Convert a String to a {@link ResourceID}.
	 * @param name
	 * @return a {@link ResourceID} representation of a String.
	 */
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
