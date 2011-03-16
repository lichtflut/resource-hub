/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.model.impl;

import java.util.Iterator;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
	Implementation of {@link PropertyAssertion}
 * </p>
 *
 * <p>
 * 	Created Mar 16, 2011
 * </p>
 *
 * @author Raphael Esterle
 */

public class PropertyAssertionImpl implements PropertyAssertion{
	
	//Instance members
	private ResourceID propertyDescriptor;
	private PropertyDeclaration property;
	private Cardinality cardinality;
	private Set<Constraint> constraints = null;
	
	//Constructors
	public PropertyAssertionImpl(ResourceID propertyDescriptor,
			PropertyDeclaration property) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.property = property;
		cardinality = CardinalityFactory.hasAtLeastOneToMany();
	}
	
	public PropertyAssertionImpl(ResourceID propertyDescriptor,
			PropertyDeclaration property, Cardinality cardinality,
			Set<Constraint> constraints) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.property = property;
		this.cardinality = cardinality;
		this.constraints = constraints;
	}

	/**
	 * @return Returns the propertyDescriptor
	 */
	public ResourceID getPropertyDescriptor() {
		return propertyDescriptor;
	}
	
	/**
	 * @return Returns the property itself
	 */
	public PropertyDeclaration getProperty() {
		return property;
	}
	
	/**
	 * @return Returns the cardinality
	 */
	public Cardinality getCardinality() {
		return cardinality;
	}
	
	/**
	 * @return Returns a {@link Set} of constraints
	 */
	public Set<Constraint> getConstraints() {
		return constraints;
	}
	
	/**
	 * @return Returns the toString()-representation of {@link PropertyAssertion} separated in '\n'
	 */
	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("propertyDescriptor: " + propertyDescriptor.toString());
		sBuffer.append("\nproperty: "+ property.toString());
		sBuffer.append("\ncardinality: "+ cardinality.toString());
		if(null!=constraints){
			Iterator i = constraints.iterator();
			while(i.hasNext())
				sBuffer.append("\ncardinality: "+i.next().toString());
		}
		return sBuffer.toString();
	}

}
