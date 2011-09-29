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
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * <p>
 *	Implementation of {@link PropertyDeclaration}.
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
public class PropertyDeclarationImpl implements PropertyDeclaration {

	private static final long serialVersionUID = -322769574493912661L;

	private ResourceID propertyDescriptor;
	
	private TypeDefinition typeDefinition;
	
	private Cardinality cardinality = CardinalityBuilder.hasOptionalOneToMany();
	
	private Set<Constraint> constraints = new HashSet<Constraint>();
	
	private String propertyIdentifier = null;

	// -----------------------------------------------------
	
	/**
	 * Constructor.
	 * @param propertyDescriptor -
	 * @param typeDefinition -
	 */
	public PropertyDeclarationImpl(final ResourceID propertyDescriptor,
			final TypeDefinition typeDefinition) {
		this.propertyDescriptor = propertyDescriptor;
		this.typeDefinition = typeDefinition;
		this.cardinality = CardinalityBuilder.hasOptionalOneToMany();
	}

	/**
	 * Constructor.
	 * @param propertyIdentifier -
	 * @param c -
	 */
	public PropertyDeclarationImpl(final String propertyIdentifier, final Cardinality c){
		this.cardinality = c;
		this.propertyIdentifier = propertyIdentifier;
	}

	/**
	 * Constructor.
	 * @param propertyIdentifier -
	 */
	public PropertyDeclarationImpl(final String propertyIdentifier){
		this.propertyIdentifier = propertyIdentifier;
	}

	/**
	 * Constructor.
	 * @param propertyDescriptor -
	 * @param property -
	 * @param cardinality -
	 * @param constraints -
	 */
	public PropertyDeclarationImpl(final ResourceID propertyDescriptor,
			final TypeDefinition property, final Cardinality cardinality,
			final Set<Constraint> constraints) {
		this.propertyDescriptor = propertyDescriptor;
		this.typeDefinition = property;
		this.cardinality = cardinality;
		this.constraints = constraints;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getPropertyType() {
		return propertyDescriptor;
	}
	
	/**
	 * Sets the property descriptor.
	 * @param propertyDescriptor -
	 */
	@Override
	public void setPropertyType(final ResourceID propertyDescriptor){
		this.propertyDescriptor = propertyDescriptor;
	}

	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeDefinition getTypeDefinition() {
		return typeDefinition;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setTypeDefinition(final TypeDefinition def) {
		this.typeDefinition = def;
	}

	// -----------------------------------------------------

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
	public void setCardinality(final Cardinality c) {
		this.cardinality = c;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Constraint> getConstraints() {
		Set<Constraint> output = new HashSet<Constraint>();
		output.addAll(getTypeDefinition().getConstraints());
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
		sBuffer.append("\nproperty: " + ((propertyDescriptor!=null) ? typeDefinition.toString() : ""));
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

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QualifiedName getQualifiedPropertyIdentifier() {
		if (this.propertyIdentifier == null) {
			return null;
		}
		if (!(QualifiedName.isUri(this.propertyIdentifier) || QualifiedName
				.isQname(this.propertyIdentifier))) {
			return new QualifiedName(VoidNamespace.getInstance(),
					this.propertyIdentifier);
		} else {
			return new QualifiedName(this.propertyIdentifier);
		}
	}

}
