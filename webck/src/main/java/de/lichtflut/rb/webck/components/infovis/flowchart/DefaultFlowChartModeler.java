/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import de.lichtflut.rb.core.RB;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
	public ResourceNode getLane(ResourceNode node) {
		for (Statement stmt : node.getAssociations()) {
			final SNProperty predicate = stmt.getPredicate().asResource().asProperty();
			if (predicate.isSubPropertyOf(RB.HAS_OWNER) && stmt.getObject().isResourceNode()) {
				return stmt.getObject().asResource(); 
			}
		}
		return null;
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
		logger.info("Predecessors of {} are: {}", node, predecessors);
		return predecessors;
	}

}
