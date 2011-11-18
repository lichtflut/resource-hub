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

import de.lichtflut.rb.core.entity.RBEntityReference;
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
	
	private final List<Object> values = new ArrayList<Object>();

	private final PropertyDeclaration declaration;
	
	private final ResourceID predicate;
	
	private final boolean isKnownToSchema;
	
	private int slots;

	//------------------------------------------------------------

	/**
	 * Constructor.
	 * @param declaration - {@link PropertyDeclaration}. <code>null</code> if assertion is not known to Schema
	 * @param values - values of this field
	 */
	public RBFieldImpl(final PropertyDeclaration declaration, final Set<SemanticNode> values) {
		this.predicate = declaration.getPropertyType();
		this.declaration = declaration;
		this.isKnownToSchema = true;
		initSlots(values); 
	}

	/**
	 * Constructor to be used when no property declaration exists.
	 * @param predicate - the predicate
	 * @param values - values of this field
	 */
	public RBFieldImpl(final ResourceID predicate, final Set<SemanticNode> values) {
		this.predicate = predicate;
		this.declaration = null;
		this.isKnownToSchema = false;
		initSlots(values); 
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
	 * {@inheritDoc}
	 */
	public int getSlots() {
		return slots;
	};
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Object getValue(final int index) {
		if (index >= slots) {
			throw new IllegalArgumentException("Index out of bounds: " + index);
		}
		return values.get(index);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(final int index, final Object value) {
		if (index > slots) {
			throw new IllegalArgumentException("Index out of bounds: " + index);
		} else if (index < slots) {
			values.remove(index);	
		} else {
			slots++;
		}
		values.add(index, value);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void addValue(final Object value) {
		values.add(value);
		slots++;
	}
	
	@Override
	public List<Object> getValues() {
		final List<Object> copy = new ArrayList<Object>(slots);
		for (Object object : values) {
			if (object != null) {
				copy.add(object);
			}
		}
		return copy;
	}

	@Override
	public void setValues(final List<Object> fieldValues) {
		this.values.clear();
		this.values.addAll(fieldValues);
	}

	// -----------------------------------------------------

	/**
	 * String representation of this field with getLabel().
	 * @return {@link String}
	 */
	@Override
	public String toString(){
		return values.toString();
	}
	
	// -----------------------------------------------------
	
	/**
	 * @param givenValues
	 */
	protected void initSlots(final Set<SemanticNode> givenValues) {
		this.slots = givenValues.size();	
		if (givenValues == null || givenValues.isEmpty()) {
			this.values.add(null);
			this.slots = 1;
		} else if (isResourceReference()) {
			addReferences(givenValues);
		} else {
			addValues(givenValues);
		}
	}
	
	protected void addReferences(final Set<SemanticNode> givenValues) {
		for (SemanticNode sn : givenValues) {
			this.values.add(new RBEntityReference(sn.asResource()));
		}
	}
	
	protected void addValues(final Set<SemanticNode> givenValues) {
		for (SemanticNode sn : givenValues) {
			this.values.add(sn.asValue().getValue());
		}
	}

}
