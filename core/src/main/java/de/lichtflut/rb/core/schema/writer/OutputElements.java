/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.schema.writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.arastreju.sge.io.NamespaceMap;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.SimpleNamespace;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

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

	private final List<Constraint> constraints = new ArrayList<Constraint>();

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
	 * @param constraints
	 */
	public void addConstraints(final Collection<Constraint> constraints) {
		for (Constraint constr : constraints) {
			this.constraints.add(constr);
			register(constr);
		}
	}

	/**
	 * @param statement
	 */
	public void add(final Statement statement) {
		this.statements.add(statement);
	}


	/**
	 * @param schemas
	 */
	public void addSchemas(final ResourceSchema... schemas) {
		for (ResourceSchema schema : schemas) {
			this.schemas.add(schema);
			register(schema);
		}
	}

	/**
	 * @param constraints
	 */
	public void addConstraints(final Constraint... constraints) {
		for (Constraint constr : constraints) {
			this.constraints.add(constr);
			register(constr);
		}
	}

	public void registerNamespace(final String namespace, final String prefix) {
		namespaceMap.addNamespace(new SimpleNamespace(namespace, prefix));
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
	public List<Constraint> getConstraints() {
		return constraints;
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
				+ constraints.size() + " constraint(s) with "
				+ statements.size() + " additional statement(s)";
	}

	// ----------------------------------------------------

	private void register(final ResourceSchema schema) {
		register(schema.getDescribedType());
		for (PropertyDeclaration decl : schema.getPropertyDeclarations()) {
			register(decl.getPropertyDescriptor());
			register(decl.getConstraint());
		}
	}

	private void register(final Constraint constr) {
		if (constr != null && constr.isPublic() && !constr.isLiteral()) {
			register(constr.getQualifiedName());
		}
	}

	private void register(final ResourceID id) {
		register(id.getQualifiedName());
	}

	private void register(final QualifiedName qn) {
		namespaceMap.addNamespace(new SimpleNamespace(qn.getNamespace()));
	}

}
