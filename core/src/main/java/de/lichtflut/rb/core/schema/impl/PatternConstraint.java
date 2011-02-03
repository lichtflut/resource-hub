/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.impl;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.Constraint;

/**
 * <p>
 *  Pattern constraint for literal values.
 * </p>
 *
 * <p>
 * 	Created Feb 3, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PatternConstraint implements Constraint {
	
	private final String pattern;
	
	// -----------------------------------------------------

	/**
	 * Constructs a new PatternConstraint.
	 * @param pattern The pattern.
	 */
	public PatternConstraint(final String pattern) {
		this.pattern = pattern;
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
		return pattern;
	}
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.Constraint#getTypeConstraing()
	 */
	public ResourceID getResourceTypeConstraint() {
		return null;
	}
	
	// -----------------------------------------------------

	public String getPattern() {
		return pattern;
	}
	
	// -----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return pattern;
	}
	
}
