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
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
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
public class RBFieldImpl implements RBField, Serializable {

	private PropertyDeclaration declaration;
	
	private ResourceID predicate;
	
	private List<Object> values;
	
	private boolean isKnownToSchema;

	//------------------------------------------------------------


	/**
	 * Constructor.
	 * @param declaration - {@link PropertyDeclaration}. <code>null</code> if assertion is not known to Schema
	 * @param values - values of this field
	 */
	public RBFieldImpl(final PropertyDeclaration declaration, final Set<SemanticNode> values) {
		if(declaration != null){
			this.declaration = declaration;
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
	public ResourceID getPredicate() {
		if(isKnownToSchema){
			return declaration.getPropertyType();
		}
		return predicate;
	}

	@Override
	public String getLabel() {
		if (isKnownToSchema) {
			return declaration.getPropertyType().getName();
		} else {
			return predicate.getName();
		}
	}

	@Override
	public List<Object> getValues() {
		return values;
	}

	@Override
	public void setValues(final List<Object> fieldValues) {
		this.values = fieldValues;
	}

	@Override
	public ElementaryDataType getDataType() {
		if (declaration != null) {
			return declaration.getTypeDefinition().getElementaryDataType();
		} else {
			return ElementaryDataType.STRING;
		}
	}

	@Override
	public Cardinality getCardinality() {
		if (declaration != null) {
			return declaration.getCardinality();
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
		if (declaration != null) {
			return declaration.getTypeDefinition().isResourceReference();
		} else {
			return false;
		}
	}

	@Override
	public Set<Constraint> getConstraints() {
		if (declaration != null) {
			return declaration.getTypeDefinition().getConstraints();
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
