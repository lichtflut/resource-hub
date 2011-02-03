/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.impl;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.Constraint;

/**
 * <p>
 *  Type constraint for resource references.
 *  This constraint is to be interpreted as follows:
 *  <pre> 
 *  	if constraint type is X and the resource applied id Y
 *  	the constraint is fulfilled
 *  	if (X rdf:type Y) is true
 *   </pre>
 * </p>
 *
 * <p>
 * 	Created Feb 3, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceTypeConstraint implements Constraint {
	
	private final ResourceID type;
	
	// -----------------------------------------------------

	/**
	 * Constructs a new ResourceTypeConstraint.
	 * @param type The type.
	 */
	public ResourceTypeConstraint(final ResourceID type) {
		this.type = type;
	}
	
	// -----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.Constraint#isLiteralConstraint()
	 */
	public boolean isLiteralConstraint() {
		return true;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.Constraint#isResourceConstraint()
	 */
	public boolean isResourceTypeConstraint() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.Constraint#getLiteralConstraint()
	 */
	public String getLiteralConstraint() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.Constraint#getTypeConstraing()
	 */
	public ResourceID getResourceTypeConstraint() {
		return type;
	}
	
	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return type.toString();
	}
	
}
