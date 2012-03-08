/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Default modeler.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class DefaultFlowChartModeler extends AbstractFlowChartModeler {
	
	private final Logger logger = LoggerFactory.getLogger(DefaultFlowChartModeler.class);
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFlowChartNode(ResourceNode node) {
		return true;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getLane(ResourceNode node) {
		return "Unassigned";
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Collection<ResourceNode> getPredecessors(ResourceNode node) {
		final Set<ResourceNode> predecessors = new HashSet<ResourceNode>();
		for (Statement stmt : node.getAssociations()) {
			final SNProperty predicate = stmt.getPredicate().asResource().asProperty();
			if (predicate.isSubPropertyOf(Aras.IS_SUCCESSOR_OF)) {
				predecessors.add(stmt.getObject().asResource());
			}
		}
		return predecessors;
	}

}
