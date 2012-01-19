/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.organizer;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.model.nodes.ValueNode;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Declaration of a context.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ContextDeclaration implements Context {

	private QualifiedName qn;
	
	private String description;
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public QualifiedName getQualifiedName() {
		return qn;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toURI() {
		return qn.toURI();
	}
	
	/**
	 * @param qn the qn to set
	 */
	public void setQualifiedName(QualifiedName qn) {
		this.qn = qn;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	// ----------------------------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public boolean isValueNode() {
		return false;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public boolean isResourceNode() {
		return true;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public ResourceNode asResource() {
		return new SNResource(qn);
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public ValueNode asValue() {
		return null;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public int compareTo(Context other) {
		return getQualifiedName().compareTo(other.getQualifiedName());
	}
	
}
