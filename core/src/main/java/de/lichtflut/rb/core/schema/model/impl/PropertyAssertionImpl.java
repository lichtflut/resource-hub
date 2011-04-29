/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */

package de.lichtflut.rb.core.schema.model.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.VoidNamespace;

import de.lichtflut.rb.core.schema.model.Cardinality;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyAssertion;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;

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
	private String propertyIdentifier = null;
	
	//Constructors
	public PropertyAssertionImpl(ResourceID propertyDescriptor,
			PropertyDeclaration property) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.property = property;
		cardinality = CardinalityFactory.hasOptionalOneToMany();
	}
	
	// -----------------------------------------------------
	
	public PropertyAssertionImpl(String propertyIdentifier, Cardinality c){
		this.cardinality = c;
		this.propertyIdentifier = propertyIdentifier;
	}
	
	// -----------------------------------------------------
	
	public PropertyAssertionImpl(String propertyIdentifier){
		this.propertyIdentifier = propertyIdentifier;
	}
	
	// -----------------------------------------------------
	
	public PropertyAssertionImpl(ResourceID propertyDescriptor,
			PropertyDeclaration property, Cardinality cardinality,
			Set<Constraint> constraints) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.property = property;
		this.cardinality = cardinality;
		this.constraints = constraints;
	}

	// -----------------------------------------------------
	
	/**
	 * @return Returns the propertyDescriptor
	 */
	public ResourceID getPropertyDescriptor() {
		return propertyDescriptor;
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return Returns the property itself
	 */
	public PropertyDeclaration getPropertyDeclaration() {
		return property;
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return Returns the cardinality
	 */
	public Cardinality getCardinality() {
		return cardinality;
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return Returns a {@link Set} of constraints, containing the direct assertion constraints and the properties constraints
	 */
	public Set<Constraint> getConstraints() {
		Set<Constraint> output = new HashSet<Constraint>();
		output.addAll(getPropertyDeclaration().getConstraints());
		output.addAll(this.constraints);
		return output;
	}
	
	// -----------------------------------------------------
	
	/**
	 * @return Returns the toString()-representation of {@link PropertyAssertion} separated in '\n'
	 */
	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("propertyIdentifier: " +  this.getPropertyIdentifier());
		sBuffer.append("\npropertyDescriptor: " + ((propertyDescriptor!=null) ? propertyDescriptor.getQualifiedName().toURI() : ""));
		sBuffer.append("\nproperty: " + ((propertyDescriptor!=null) ? property.toString() : ""));
		sBuffer.append("\ncardinality: "+ cardinality.toString());
		if(null!=constraints){
			Iterator<Constraint> i = constraints.iterator();
			while(i.hasNext())
				sBuffer.append("\ncardinality: "+i.next().toString());
		}
		return sBuffer.toString();
	}

	// -----------------------------------------------------
	
	public boolean isResolved() {
		if(property == null) return false;
		return true;
	}

	// -----------------------------------------------------
	
	public void setCardinality(Cardinality c) {
		this.cardinality = c;
		
	}

	// -----------------------------------------------------
	
	public void setPropertyIdentifier(String identifier) {
		this.propertyIdentifier = identifier;
		
	}

	// -----------------------------------------------------
	
	public String getPropertyIdentifier() {
		if(this.propertyIdentifier==null) return null;
		if(!(QualifiedName.isUri(this.propertyIdentifier) || QualifiedName.isQname(this.propertyIdentifier)))
			return  new QualifiedName(VoidNamespace.getInstance(),this.propertyIdentifier).toURI();
		return this.propertyIdentifier;
	}

	// -----------------------------------------------------
	
	public QualifiedName getQualifiedPropertyIdentifier() {
		if(this.propertyIdentifier==null) return null;
		if(!(QualifiedName.isUri(this.propertyIdentifier) || QualifiedName.isQname(this.propertyIdentifier)))
			return new QualifiedName(this.propertyIdentifier);
		else{
			 return new QualifiedName(VoidNamespace.getInstance(),this.propertyIdentifier);
		}
	}

}
