/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import java.util.Locale;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.VisualizationInfo;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.PlainVisualizationInfo;

/**
 * <p>
 *  A field not declared by a schema's property delcaration.
 * </p>
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
public class UndeclaredRBField extends AbstractRBField {

	private final ResourceID predicate;

	//------------------------------------------------------------

	/**
	 * Constructor to be used when no property declaration exists.
	 * @param predicate - the predicate
	 * @param statements - statements of this field
	 */
	public UndeclaredRBField(final ResourceID predicate, final Set<Statement> statements) {
		super(statements);
		this.predicate = predicate;
	}

	//------------------------------------------------------------

	@Override
	public ResourceID getPredicate() {
		return predicate;
	}

	@Override
	public String getLabel(final Locale locale) {
		return ResourceLabelBuilder.getInstance().getFieldLabel(predicate, locale);
	}

	@Override
	public Datatype getDataType() {
		return Datatype.STRING;
	}

	@Override
	public Cardinality getCardinality() {
		return CardinalityBuilder.hasOptionalOneToMany();
	}

	@Override
	public boolean isResourceReference() {
		return false;
	}

	@Override
	public VisualizationInfo getVisualizationInfo() {
		return new PlainVisualizationInfo();
	}

	@Override
	public Constraint getConstraint() {
		return null;
	}

}
