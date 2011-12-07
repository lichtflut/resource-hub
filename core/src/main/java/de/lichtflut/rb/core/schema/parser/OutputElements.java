/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.LabelBuilder;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * <p>
 * Elements for schema output.
 * </p>
 *
 * <p>
 * 	Created Dec 7, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class OutputElements {

	private final List<ResourceSchema> schemas = new ArrayList<ResourceSchema>();
	
	private final List<TypeDefinition> typeDefs = new ArrayList<TypeDefinition>();
	
	private final List<Statement> statements = new ArrayList<Statement>();
	
	private final NamespaceMap namespaceMap = new NamespaceMap();
	
	// -----------------------------------------------------

	/**
	 * @param schemas
	 */
	public void addSchemas(final Collection<ResourceSchema> schemas) {
		for (ResourceSchema schema : schemas) {
			this.schemas.add(schema);
			register(schema);
		}
	}

	/**
	 * @param typeDefs
	 */
	public void addTypeDefs(final Collection<TypeDefinition> typeDefs) {
		this.typeDefs.addAll(typeDefs);
		for (TypeDefinition def : typeDefs) {
			this.typeDefs.add(def);
			register(def);
		}
	}
	
	/**
	 * @param statement
	 */
	public void add(final Statement statement) {
		this.statements.add(statement);
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return the namespaceMap
	 */
	public NamespaceMap getNamespaceMap() {
		return namespaceMap;
	}
	
	/**
	 * @return the schemas
	 */
	public List<ResourceSchema> getSchemas() {
		return schemas;
	}
	
	/**
	 * @return the typeDefs
	 */
	public List<TypeDefinition> getTypeDefs() {
		return typeDefs;
	}
	
	/**
	 * @return additional statements.
	 */
	public List<Statement> getStatements() {
		return statements;
	}
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return schemas.size() + " schema(s) and " 
				+ typeDefs.size() + " type definition(s) with "
				+ statements.size() + " additional statement(s)"; 
	}
	
	// ----------------------------------------------------
	
	private void register(final ResourceSchema schema) {
		register(schema.getDescribedType());
		for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			register(decl.getPropertyDescriptor());
			register(decl.getTypeDefinition());
		}
		LabelBuilder labelBuilder = schema.getLabelBuilder();
		
	}
	
	private void register(final TypeDefinition def) {
		if (def.isPublicTypeDef()) {
			register(def.getID());
		} else {
			for (Constraint constraint : def.getConstraints()) {
				if (constraint.isResourceTypeConstraint()) {
					register(constraint.getResourceTypeConstraint());
				}
			}
		}
	}
	
	private void register(final ResourceID id) {
		register(id.getQualifiedName());
	}
	
	private void register(final QualifiedName qn) {
		namespaceMap.addNamespace(qn.getNamespace());
	}
	
}
