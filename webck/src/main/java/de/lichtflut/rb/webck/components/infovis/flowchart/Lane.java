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
package de.lichtflut.rb.webck.components.infovis.flowchart;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.Locale;

/**
 * <p>
 *  A lane in a flow chart.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class Lane {
	
	private ResourceNode node;
	
	private String id;
	
	// ----------------------------------------------------

	/**
	 * @param title
	 * @param id
	 */
	public Lane(String id) {
		this.id = id;
	}
	
	/**
	 * @param title
	 * @param id
	 */
	public Lane(ResourceNode node, String id) {
		this(id);
		this.node = node;
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the technical ID of this lane.
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * @return The URI of the node represented by this lane.
	 */
	public String getURI() {
		if (node != null) {
			return node.toURI();
		} else {
			return "";
		}
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		if (node != null) {
			return ResourceLabelBuilder.getInstance().getLabel(node, Locale.getDefault());
		} else {
			return "Unnassigned";
		}
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Lane) {
			Lane other = (Lane) obj;
			return Infra.equals(id, other.getID());
		} else {
			return super.equals(obj);
		}
	}

}
