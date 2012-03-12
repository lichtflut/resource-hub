/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import java.util.Locale;

import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.common.ResourceLabelBuilder;

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
