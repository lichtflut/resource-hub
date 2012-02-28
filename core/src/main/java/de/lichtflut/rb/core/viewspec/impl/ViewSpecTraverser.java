/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.traverse.GraphBuilder;
import org.arastreju.sge.traverse.GraphTraverser;
import org.arastreju.sge.traverse.PredicateFilter;
import org.arastreju.sge.traverse.StatementVisitor;
import org.arastreju.sge.traverse.TraversalFilter;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  Exports view spec elements to a semantic graph.
 * </p>
 *
 * <p>
 * 	Created Jan 26, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ViewSpecTraverser {
	
	public SemanticGraph toGraph(WidgetSpec spec) {
		final GraphBuilder builder = new GraphBuilder(createFilter());
		builder.addStatements(spec.getAssociations());
		builder.addCascading(spec);
		return builder.getGraph();
	}
	
	public SemanticGraph toGraph(Perspective spec) {
		final GraphBuilder builder = new GraphBuilder(createFilter());
		builder.addStatements(spec.getAssociations());
		builder.addCascading(spec);
		return builder.getGraph();
	}
	
	public void traverse(WidgetSpec spec, StatementVisitor visitor) {
		new GraphTraverser(createFilter(), visitor).start(spec);
	}
	
	// ----------------------------------------------------
	
	private TraversalFilter createFilter() {
		return new PredicateFilter()
			.addFollow(
				// VIEW_PORTS
				WDGT.HAS_VIEW_PORT,
				WDGT.CONTAINS_WIDGET,
				// LAYOUT
				WDGT.HAS_LAYOUT,
				// SELECTION
				WDGT.HAS_SELECTION,
				WDGT.HAS_EXPRESSION,
				WDGT.HAS_OPERATOR,
				WDGT.HAS_PARAMETER,
				// COLUMNS
				WDGT.DEFINES_COLUMN,
				// PREDEFINED
				WDGT.IS_IMPLEMENTED_BY_CLASS,
				// ACTIONS
				WDGT.SUPPORTS_ACTION,
				WDGT.ACTION_CREATE_INSTANCE_OF
			).addAllowed(
				RDF.TYPE,
				RDFS.LABEL,
				Aras.HAS_SERIAL_NUMBER,
				// COMMON
				RB.HAS_TITLE,
				RB.HAS_NAME,
				RB.HAS_ID,
				RB.HAS_DESCRIPTION,
				// END NODES
				WDGT.HAS_HEADER,
				WDGT.HAS_TERM,
				WDGT.CONCERNS_FIELD,
				WDGT.CORRESPONDS_TO_PROPERTY
			);
	}

}
