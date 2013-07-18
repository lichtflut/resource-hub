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
package de.lichtflut.rb.core.viewspec.impl;

import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryParser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  Implementation of a selection. Each selection consists of
 *  <ul>
 *  	<li>Expressions and/or</li>
 *  	<li>Parameters</li>
 *  </ul>
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNSelection extends ResourceView implements Selection {

    static ResourceID[] PREDICATES = new ResourceID[] {
        WDGT.SELECT_BY_QUERY, WDGT.SELECT_BY_TYPE, WDGT.SELECT_BY_RELATION, WDGT.SELECT_BY_VALUE, WDGT.SELECT_BY_SCRIPT
    };

    // ----------------------------------------------------

    public static SNSelection from(SemanticNode node) {
        if (node instanceof SNSelection) {
            return (SNSelection) node;
        } else if (node instanceof ResourceNode) {
            return new SNSelection((ResourceNode) node);
        } else if (node instanceof ResourceID) {
            return new SNSelection(node.asResource());
        } else {
            return null;
        }
    }

    public static SNSelection byType(ResourceID type) {
        SNSelection selection = new SNSelection();
        selection.addAssociation(WDGT.SELECT_BY_TYPE, type);
        return selection;
    }

    public static SNSelection byRelation(ResourceID type) {
        SNSelection selection = new SNSelection();
        selection.addAssociation(WDGT.SELECT_BY_RELATION, type);
        return selection;
    }

    public static SNSelection byValue(String value) {
        SNSelection selection = new SNSelection();
        selection.addAssociation(WDGT.SELECT_BY_VALUE, new SNText(value));
        return selection;
    }

    public static SNSelection byQuery(String query) {
        SNSelection selection = new SNSelection();
        selection.addAssociation(WDGT.SELECT_BY_QUERY, new SNText(query));
        return selection;
    }

    public static SNSelection byScript(String script) {
        SNSelection selection = new SNSelection();
        selection.addAssociation(WDGT.SELECT_BY_SCRIPT, new SNText(script));
        return selection;
    }

    // ----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public SNSelection() {
		super();
	}

	/**
	 * Constructor.
	 * @param resource The resource to be wrapped.
	 */
	public SNSelection(ResourceNode resource) {
		super(resource);
	}
	
	// ----------------------------------------------------

    @Override
    public SelectionType getType() {
        for (Statement statement : getAssociations()) {
            ResourceID predicate = statement.getPredicate();
            if (WDGT.SELECT_BY_QUERY.equals(predicate)) {
                return SelectionType.BY_QUERY;
            } else if (WDGT.SELECT_BY_TYPE.equals(predicate)) {
                return SelectionType.BY_TYPE;
            } else if (WDGT.SELECT_BY_RELATION.equals(predicate)) {
                return SelectionType.BY_RELATION;
            } else if (WDGT.SELECT_BY_VALUE.equals(predicate)) {
                return SelectionType.BY_VALUE;
            } else if (WDGT.SELECT_BY_SCRIPT.equals(predicate)) {
                return SelectionType.BY_SCRIPT;
            }
        }
        return null;
    }

    @Override
    public SemanticNode getQueryExpression() {
        final Set<ResourceID> predicates = new HashSet<ResourceID>(Arrays.asList(PREDICATES));
        for (Statement statement : getAssociations()) {
            if (predicates.contains(statement.getPredicate())) {
                return statement.getObject();
            }
        }
        return null;
    }

    // ----------------------------------------------------

    @Override
	public void adapt(Query query) {
        for (Statement statement : getAssociations()) {
            ResourceID predicate = statement.getPredicate();
            if (WDGT.SELECT_BY_QUERY.equals(predicate)) {
                adaptByQuery(query);
                return;
            } else if (WDGT.SELECT_BY_TYPE.equals(predicate)) {
                adaptByType(query);
                return;
            } else if (WDGT.SELECT_BY_RELATION.equals(predicate)) {
                adaptByRelation(query);
                return;
            } else if (WDGT.SELECT_BY_VALUE.equals(predicate)) {
                adaptByValue(query);
                return;
            } else if (WDGT.SELECT_BY_SCRIPT.equals(predicate)) {
                // Scripts can not be adapted to queries.
                return;
            }
        }
	}

    @Override
	public boolean isDefined() {
		return getType() != null;
	}

	// ----------------------------------------------------
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Selection[" + getType() + "]");
        sb.append(";").append("expr=\"").append(getQueryExpression()).append("\"");
		return sb.toString();
	}

    // ----------------------------------------------------

    private void adaptByQuery(Query query) {
        String qs = stringValue(WDGT.SELECT_BY_QUERY);
        new QueryParser().adapt(query, qs);
    }

    private void adaptByType(Query query) {
        ResourceID type = resourceValue(WDGT.SELECT_BY_TYPE);
        query.addField(RDF.TYPE, type);
    }

    private void adaptByValue(Query query) {
        String value = stringValue(WDGT.SELECT_BY_VALUE);
        query.addValue(value);
    }

    private void adaptByRelation(Query query) {
        ResourceID rel = resourceValue(WDGT.SELECT_BY_RELATION);
        query.addRelation(rel.toURI());
    }

}
