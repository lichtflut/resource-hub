/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;

/**
 * Represents a resource type defined by a RBResourceSchema.
 *
 * Created: Aug 8, 2011
 *
 * @author Ravi Knox
 */
@SuppressWarnings("serial")
public class RBField implements IRBField, Serializable {

	private PropertyAssertion assertion;
	private List<Object> values;
	private boolean isKnownToSchema;

	//------------------------------------------------------------


	/**
	 * Constructor.
	 * @param assertion - {@link PropertyAssertion}
	 * @param values - values of this field
	 */
	public RBField(final PropertyAssertion assertion, final Set<SemanticNode> values) {
		if(assertion != null){
			this.assertion = assertion;
			this.isKnownToSchema = true;
		}
		this.values = new ArrayList<Object>();
		this.values.addAll(values);
	}

	//------------------------------------------------------------

	@Override
	public String getFieldName() {
		return assertion.getPropertyDeclaration().getIdentifierString();
	}

	@Override
	public String getLabel() {
		return assertion.getPropertyDeclaration().getName();
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
		return assertion.getPropertyDeclaration().getElementaryDataType();
	}

	@Override
	public Cardinality getCardinality() {
		return assertion.getCardinality();
	}

	@Override
	public boolean isKnownToSchema() {
		return isKnownToSchema;
	}

	@Override
	public boolean isResourceReference() {
		return assertion.getPropertyDeclaration().isResourceReference();
	}

	@Override
	public Set<Constraint> getConstraints() {
		return assertion.getConstraints();
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
