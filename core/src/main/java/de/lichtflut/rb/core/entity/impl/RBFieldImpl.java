/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;

/**
 * Represents a resource type defined by a RBResourceSchema.
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class RBFieldImpl implements RBField, Serializable {

	private PropertyAssertion assertion;
	private ResourceID predicate;
	private List<Object> values;
	private boolean isKnownToSchema;

	//------------------------------------------------------------


	/**
	 * Constructor.
	 * @param assertion - {@link PropertyAssertion}. <code>null</code> if assertion is not known to Schema
	 * @param values - values of this field
	 */
	public RBFieldImpl(final PropertyAssertion assertion, final Set<SemanticNode> values) {
		if(assertion != null){
			this.assertion = assertion;
			this.isKnownToSchema = true;
		}
		this.values = new ArrayList<Object>();
		if(values != null){
			this.values.addAll(values);
		}
	}

	/**
	 * Constructor.
	 * @param predicate - the predicate
	 * @param values - values of this field
	 */
	public RBFieldImpl(final ResourceID predicate, final Set<SemanticNode> values) {
		this.predicate = predicate;
		this.isKnownToSchema = false;
		this.values = new ArrayList<Object>();
		if(values != null){
			this.values.addAll(values);
		}
	}

	//------------------------------------------------------------

	@Override
	public String getFieldName() {
		if(isKnownToSchema){
			return assertion.getPropertyDeclaration().getName();
		}
		return predicate.toString();
	}

	@Override
	public String getLabel() {
		if (isKnownToSchema) {
			return assertion.getPropertyDeclaration().getName();
		} else {
			return predicate.getName();
		}
	}

	@Override
	public List<Object> getFieldValues() {
		return values;
	}

	@Override
	public void setFieldValues(final List<Object> fieldValues) {
		this.values = fieldValues;
	}

	@Override
	public ElementaryDataType getDataType() {
		if (assertion != null) {
			return assertion.getPropertyDeclaration().getElementaryDataType();
		} else {
			return ElementaryDataType.STRING;
		}
	}

	@Override
	public Cardinality getCardinality() {
		if (assertion != null) {
			return assertion.getCardinality();
		} else {
			return CardinalityBuilder.hasOptionalOneToMany();
		}
	}

	@Override
	public boolean isKnownToSchema() {
		return isKnownToSchema;
	}

	@Override
	public boolean isResourceReference() {
		if (assertion != null) {
			return assertion.getPropertyDeclaration().isResourceReference();
		} else {
			return false;
		}
	}

	@Override
	public Set<Constraint> getConstraints() {
		if (assertion != null) {
			return assertion.getPropertyDeclaration().getConstraints();
		} else {
			return Collections.emptySet();
		}
	}

	// -----------------------------------------------------

	/**
	 * String representation of this field with getLabel().
	 * @return {@link String}
	 */
	@Override
	public String toString(){
		return this.getLabel();
	}

}
