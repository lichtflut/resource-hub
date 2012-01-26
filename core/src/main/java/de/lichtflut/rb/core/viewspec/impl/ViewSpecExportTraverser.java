/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.traverse.GraphBuilder;
import org.arastreju.sge.traverse.PredicateFilter;
import org.arastreju.sge.traverse.TraversalFilter;

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
public class ViewSpecExportTraverser {

	public SemanticGraph toGraph(WidgetSpec spec) {
		final GraphBuilder builder = new GraphBuilder(createFilter());
		builder.addStatements(spec.getAssociations());
		builder.addCascading(spec);
		return builder.getGraph();
	}
	
	private TraversalFilter createFilter() {
		return new PredicateFilter(new ResourceID[] {
				// LAYOUT
				WDGT.HAS_LAYOUT,
				// SELECTION
				WDGT.HAS_SELECTION,
				WDGT.HAS_EXPRESSION,
				WDGT.HAS_OPERATOR,
				WDGT.HAS_PARAMETER,
				WDGT.CONCERNS_FIELD,
				WDGT.HAS_TERM,
				// COLUMNS
				WDGT.DEFINES_COLUMN,
				WDGT.CORRESPONDS_TO_PROPERTY,
		});
	}

}
