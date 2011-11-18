/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import java.util.Collections;
import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;

/**
 * <p>
 * Default implementation of {@link RBField}.
 * </p> 
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class UndeclaredRBField extends AbstractRBField {
	
	private final ResourceID predicate;

	//------------------------------------------------------------

	/**
	 * Constructor to be used when no property declaration exists.
	 * @param predicate - the predicate
	 * @param values - values of this field
	 */
	public UndeclaredRBField(final ResourceID predicate, final Set<SemanticNode> values) {
		super(values, false);
		this.predicate = predicate;
	}

	//------------------------------------------------------------

	@Override
	public ResourceID getPredicate() {
		return predicate;
	}

	@Override
	public ElementaryDataType getDataType() {
		return ElementaryDataType.STRING;
	}

	@Override
	public Cardinality getCardinality() {
		return CardinalityBuilder.hasOptionalOneToMany();
	}

	@Override
	public boolean isKnownToSchema() {
		return false;
	}

	@Override
	public boolean isResourceReference() {
		return false;
	}

	@Override
	public Set<Constraint> getConstraints() {
		return Collections.emptySet();
	}
	
}
