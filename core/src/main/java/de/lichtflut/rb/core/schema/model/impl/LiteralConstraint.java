/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.Collections;
import java.util.List;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;

/**
 * <p>
 * Implementation of {@link Constraint}. This class holds private literal Constraints.
 * </p>
 * Created: May 25, 2012
 *
 * @author Ravi Knox
 */
public class LiteralConstraint implements Constraint {

	private ResourceNode node;
	
	// ---------------- Constructor -------------------------
	
	/**
	 * Constructor.
	 */
	public LiteralConstraint(ResourceNode node) {
		this.node = node;
	}

	// ------------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return SNOPS.singleObject(node, RBSchema.HAS_CONSTRAINT_VALUE).asValue().getStringValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLiteralConstraint() {
		return SNOPS.singleObject(node, RBSchema.HAS_CONSTRAINT_VALUE).asValue().getStringValue();
	}

	public void setLiteralPattern(String pattern) {
		SNOPS.assure(node, RBSchema.HAS_CONSTRAINT_VALUE, pattern);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getReference() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPublic() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean holdsReference() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Datatype> getApplicableDatatypes() {
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLiteral() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceNode asResourceNode() {
		return node;
	}

}
