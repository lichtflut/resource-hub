/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.common;

import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Cross reference link to another resource.
 * </p>
 *
 * <p>
 * 	Created Mar 12, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CrossReferenceLink {
	
	private String field;
	
	private ResourceNode target;
	
	// ----------------------------------------------------
	

	/**
	 * @param field
	 * @param target
	 */
	public CrossReferenceLink(String field, ResourceNode target) {
		this.field = field;
		this.target = target;
	}
	
	// ----------------------------------------------------
	

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the target
	 */
	public ResourceNode getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(ResourceNode target) {
		this.target = target;
	}
	

}
