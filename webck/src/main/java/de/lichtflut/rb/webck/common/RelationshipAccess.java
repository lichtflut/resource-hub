/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.infra.data.MultiMap;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;

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
	
	public final static Set<ResourceID> BLACKLIST = new HashSet<ResourceID>() {{
		add(RDF.TYPE);
		add(RDFS.SUB_CLASS_OF);
		add(RDFS.LABEL);
		add(RBSystem.HAS_FIELD_LABEL);
		add(RBSystem.HAS_IMAGE);
	}};
	
	private final IModel<RBEntity> source;

	// ----------------------------------------------------
	
	/**
	 * @param source
	 */
	public RelationshipAccess(IModel<RBEntity> source) {
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
		final RBEntity entity = source.getObject();
		if (entity == null) {
			return Collections.emptyList();
		}
		final List<Statement> result = new ArrayList<Statement>();
		final Set<ResourceID> declared = getDeclaredPredicates(entity);
		final ResourceNode node = entity.getNode();

		for(Statement stmt: node.getAssociations()) {
			if (stmt.getObject().isResourceNode() &&
				!BLACKLIST.contains(stmt.getPredicate()) &&
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
