/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.hierarchy;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.structure.OrderBySerialNumber;
import org.arastreju.sge.traverse.TraversalFilter;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.infovis.AbstractJsonStream;

/**
 * <p>
 *  Json stream.
 * </p>
 *
 * <p>
 * 	Created Dec 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
abstract class HierarchyJsonStream extends AbstractJsonStream {

	private final ResourceID root;
	private final TraversalFilter filter;
	
	// ----------------------------------------------------

	/**
	 * @param root
	 */
	public HierarchyJsonStream(final ResourceID root, TraversalFilter filter) {
		this.root = root;
		this.filter = filter;
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void write(OutputStreamWriter writer) throws IOException {
		final Set<SemanticNode> written = new HashSet<SemanticNode>();
		writer.write("var root = ");
		ResourceNode rootNode = getServiceProvider().getResourceResolver().resolve(root);
		appendNode(rootNode, writer, written);
		writer.flush();
	}
	
	// ----------------------------------------------------
	
	public abstract ServiceProvider getServiceProvider(); 
	
	// ----------------------------------------------------

	private void appendNode(final ResourceNode node, OutputStreamWriter writer, Set<SemanticNode> written) throws IOException {
		written.add(node);
		writer.append("{");
		writer.append("id: \"" +  node.getQualifiedName() + "\",\n");
		writer.append("name: \"" +  label(node) + "\",\n");
		writer.append("data: {},\n");
		writer.append("children: [");
		appendChildren(node, writer, written); 
		writer.append("]\n");
		writer.append("}");
	}
	
	/**
	 * @param node
	 * @param writer
	 * @return
	 */
	private void appendChildren(ResourceNode node, OutputStreamWriter writer, Set<SemanticNode> written) throws IOException {
		final List<ResourceNode> list = new ArrayList<ResourceNode>();
		for(Statement stmt : node.getAssociations()) {
			if (!stmt.getObject().isResourceNode() || written.contains(stmt.getObject())) {
				continue;
			}
			switch (filter.accept(stmt)) {
			case STOP:
				break;
			case ACCEPPT_CONTINUE:
			case ACCEPT:
				list.add(stmt.getObject().asResource());
			default:
				break;
			}
		}
		
		Collections.sort(list, new OrderBySerialNumber());
		boolean first = true;
		for (ResourceNode current : list) {
			if (first) {
				first = false;	
			} else {
				writer.append(", ");
			}
			appendNode(current, writer, written);
		}
		
	}

	/**
	 * @param node
	 * @return
	 */
	private String label(ResourceNode node) {
		return ResourceLabelBuilder.getInstance().getLabel(node, getLocale());
	}
	
}