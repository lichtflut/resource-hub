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
package de.lichtflut.rb.webck.common;

import de.lichtflut.infra.data.MultiMap;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Accessor for relationships of an {@link RBEntity}.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RelationshipAccess implements Serializable {
	
	private final static Set<ResourceID> DEFAULT_BLACKLIST = new HashSet<ResourceID>() {{
		add(RDF.TYPE);
		add(RDFS.SUB_CLASS_OF);
		add(RDFS.LABEL);
		add(RBSystem.HAS_FIELD_LABEL);
		add(RBSystem.HAS_IMAGE);
        add(RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE);
        add(RBSystem.BELONGS_TO_PERCEPTION);
	}};
	
	private final RBEntity source;

	// ----------------------------------------------------
	
	/**
     * Constructor.
	 * @param source The source entity for the relationships.
	 */
	public RelationshipAccess(RBEntity source) {
		this.source = source;
	}
	
	// ----------------------------------------------------
	
	public MultiMap<ResourceNode, Statement> getEntityMap(RelationshipFilter filter) {
		final MultiMap<ResourceNode, Statement> map = new MultiMap<ResourceNode, Statement>();
		final List<Statement> statements = filter(filter);
		for (Statement stmt : statements) {
			map.add(stmt.getObject().asResource(), stmt);
		}
		return map;
	}
	
	public List<Statement> getStatements(RelationshipFilter filter) {
		final List<Statement> statements = filter(filter);
		Collections.sort(statements, new Comparator<Statement>() {
			@Override
			public int compare(Statement s1, Statement s2) {
				final QualifiedName q1 = s1.getObject().asResource().getQualifiedName();
				final QualifiedName q2 = s2.getObject().asResource().getQualifiedName();
				return q1.compareTo(q2);
			}
		});
		return statements;
	}

	// ----------------------------------------------------
	
	protected List<Statement> filter(RelationshipFilter filter) {
		if (source == null) {
			return Collections.emptyList();
		}
		final List<Statement> result = new ArrayList<Statement>();
		final Set<ResourceID> declared = getDeclaredPredicates(source);
		final ResourceNode node = source.getNode();

		for(Statement stmt: node.getAssociations()) {
			if (stmt.getObject().isResourceNode() &&
				!DEFAULT_BLACKLIST.contains(stmt.getPredicate()) &&
				filter.accept(stmt, declared.contains(stmt.getPredicate()))) 
			{
				result.add(stmt);
			}
		}
		return result;
	}
		
	protected Set<ResourceID> getDeclaredPredicates(final RBEntity entity) {
		final Set<ResourceID> predicates = new HashSet<ResourceID>(); 
		for(RBField field :entity.getAllFields()) {
			predicates.add(field.getPredicate());
		}
		return predicates;
	}
	
}
