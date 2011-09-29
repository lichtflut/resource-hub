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
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * <p>
 *	Implementation of {@link PropertyAssertion}.
 * </p>
 *
 * <p>
 *  This instance is initialized with the following set of default values:
 * 	<ol>
 * 	 <li>Cardinality = (min=0, max=infinity)</li>
 * 	 <li>Constraints = an empty set</li>
 *  </ol>
 * </p>
 * <p>
 * 	Created Mar 16, 2011
 * </p>
 *
 * @author Raphael Esterle
 */
public class PropertyAssertionImpl implements PropertyAssertion {

	private static final long serialVersionUID = -322769574493912661L;
	//Instance members
	private ResourceID propertyDescriptor;
	private TypeDefinition property;
	//Setting up the default cardinality
	private Cardinality cardinality = CardinalityBuilder.hasOptionalOneToMany();
	private Set<Constraint> constraints = new HashSet<Constraint>();
	private String propertyIdentifier = null;

	/**
	 * Constructor.
	 * @param propertyDescriptor -
	 * @param property -
	 */
	public PropertyAssertionImpl(final ResourceID propertyDescriptor,
			final TypeDefinition property) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.property = property;
		cardinality = CardinalityBuilder.hasOptionalOneToMany();
	}

	/**
	 * Constructor.
	 * @param propertyIdentifier -
	 * @param c -
	 */
	public PropertyAssertionImpl(final String propertyIdentifier, final Cardinality c){
		this.cardinality = c;
		this.propertyIdentifier = propertyIdentifier;
	}

	/**
	 * Constructor.
	 * @param propertyIdentifier -
	 */
	public PropertyAssertionImpl(final String propertyIdentifier){
		this.propertyIdentifier = propertyIdentifier;
	}

	/**
	 * Constructor.
	 * @param propertyDescriptor -
	 * @param property -
	 * @param cardinality -
	 * @param constraints -
	 */
	public PropertyAssertionImpl(final ResourceID propertyDescriptor,
			final TypeDefinition property, final Cardinality cardinality,
			final Set<Constraint> constraints) {
		super();
		this.propertyDescriptor = propertyDescriptor;
		this.property = property;
		this.cardinality = cardinality;
		this.constraints = constraints;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getPropertyDescriptor() {
		return propertyDescriptor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeDefinition getPropertyDeclaration() {
		return property;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cardinality getCardinality() {
		return cardinality;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Constraint> getConstraints() {
		Set<Constraint> output = new HashSet<Constraint>();
		output.addAll(getPropertyDeclaration().getConstraints());
		output.addAll(this.constraints);
		return output;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("propertyIdentifier: " +  this.getQualifiedPropertyIdentifier());
		sBuffer.append("\npropertyDescriptor: " + ((propertyDescriptor!=null)
					? propertyDescriptor.getQualifiedName().toURI() : ""));
		sBuffer.append("\nproperty: " + ((propertyDescriptor!=null) ? property.toString() : ""));
		sBuffer.append("\ncardinality: "+ cardinality.toString());
		if(null!=constraints){
			Iterator<Constraint> i = constraints.iterator();
			while(i.hasNext()){
				sBuffer.append("\ncardinality: "+i.next().toString());
			}
		}
		return sBuffer.toString();
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResolved() {
		if(property == null){
			return false;
		}
		return true;
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCardinality(final Cardinality c) {
		this.cardinality = c;
	}

	// -----------------------------------------------------

	/**
	 * Sets the property identifier.
	 * @param identifier -
	 */
	public void setPropertyIdentifier(final String identifier) {
		this.propertyIdentifier = identifier;
		//TODO This must form a valid URI
		if(!(QualifiedName.isUri(this.propertyIdentifier) || QualifiedName.isQname(this.propertyIdentifier))){
			this.propertyIdentifier =  new QualifiedName(VoidNamespace.getInstance(),this.propertyIdentifier).toURI();
		}
	}

	/**
	 * Sets the property descriptor.
	 * @param propertyDescriptor -
	 */
	public void setPropertyDescriptor(final ResourceID propertyDescriptor){
		this.propertyDescriptor = propertyDescriptor;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QualifiedName getQualifiedPropertyIdentifier() {
		if(this.propertyIdentifier==null){
			return null;
		}
		if(!(QualifiedName.isUri(this.propertyIdentifier) || QualifiedName.isQname(this.propertyIdentifier))){
			return new QualifiedName(VoidNamespace.getInstance(),this.propertyIdentifier);
	}else{
			return new QualifiedName(this.propertyIdentifier);
		}
	}

}
