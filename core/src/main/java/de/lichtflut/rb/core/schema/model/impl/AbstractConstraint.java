/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import org.arastreju.sge.model.ResourceID;
import de.lichtflut.rb.core.schema.model.Constraint;

/**
 * <p>
 * This abstract class is implementing the {@link Constraint}-interface and all specified methods as stubs within
 * This class is building a negative default for all concrete subtypes.
 * </p>
 *
 *
 * <p>
 * Created: Apr 12, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public abstract class AbstractConstraint implements Constraint{

	// -----------------------------------------------------

	private static final long serialVersionUID = 6062840670338394909L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLiteralConstraint() {
		return null;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getResourceTypeConstraint() {
		return null;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLiteralConstraint() {
		return false;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResourceTypeConstraint() {
		return false;
	}

	/**
	 * TODO: Fix toString().
	 * Nature of Constraint is not the best to implement an universal flexible toString() method
	 * @return String
	 */
	public String toString(){
		String output = "Type of Constraint is: "  + (isLiteralConstraint()
									? "Literal with " + getLiteralConstraint().toString()
									: "Resource with " + getResourceTypeConstraint().toString());
		return output;
	}
}
