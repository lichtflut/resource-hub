/*
 * Copyright 2009 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.Constraint#getLiteralConstraint()
	 * STUB-method
	 */
	public String getLiteralConstraint() {
		return null;
	}

	// -----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.Constraint#getResourceTypeConstraint()
	 * STUB-method
	 */
	public ResourceID getResourceTypeConstraint() {
		return null;
	}

	// -----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.Constraint#isLiteralContraint()
	 * STUB-method
	 */
	public boolean isLiteralConstraint() {
		return false;
	}

	// -----------------------------------------------------
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.core.schema.Constraint#isResourceTypeContraint()
	 * STUB-method
	 */
	public boolean isResourceTypeConstraint() {
		return false;
	}

}
