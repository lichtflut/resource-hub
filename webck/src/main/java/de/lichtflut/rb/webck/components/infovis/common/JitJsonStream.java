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
package de.lichtflut.rb.webck.components.infovis.common;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.webck.components.infovis.AbstractJsonStream;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.structure.OrderBySerialNumber;
import org.arastreju.sge.traverse.NotPredicateFilter;
import org.arastreju.sge.traverse.TraversalFilter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class JitJsonStream extends AbstractJsonStream {

	private final ResourceNode root;
	private final TraversalFilter filter;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param root The root of the periphery view.
	 */
	public JitJsonStream(final ResourceNode root) {
		this(root, new NotPredicateFilter(RDF.TYPE, RDFS.SUB_CLASS_OF));
	}

	/**
	 * Constructor.
	 * @param root The root of the periphery view.
	 * @param filter The filter to use.
	 */
	public JitJsonStream(final ResourceNode root, final TraversalFilter filter) {
		this.root = root;
		this.filter = filter;
	}

	// ----------------------------------------------------

	@Override
	public void write(final OutputStreamWriter writer) throws IOException {
		final Set<SemanticNode> written = new HashSet<SemanticNode>();
		writer.write("var root = ");
		appendNode(root, writer, written);
		writer.flush();
	}

	// ----------------------------------------------------

	private void appendNode(final ResourceNode node, final OutputStreamWriter writer, final Set<SemanticNode> written) throws IOException {
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
	private void appendChildren(final ResourceNode node, final OutputStreamWriter writer, final Set<SemanticNode> written) throws IOException {
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
					written.add(stmt.getObject());
					break;
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
	private String label(final ResourceNode node) {
		return ResourceLabelBuilder.getInstance().getLabel(node, getLocale());
	}

}