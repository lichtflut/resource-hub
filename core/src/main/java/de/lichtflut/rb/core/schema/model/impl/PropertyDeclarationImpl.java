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
	
	private String oldIdentifier = null;

	// -----------------------------------------------------
	
	/**
	 * Default Constructor.
	 */
	public PropertyDeclarationImpl() {
	}
	
	/**
	 * Constructor.
	 * @param propertyDescriptor -
	 * @param typeDefinition -
	 */
	public PropertyDeclarationImpl(final ResourceID propertyDescriptor,	final TypeDefinition typeDefinition) {
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
		this.oldIdentifier = propertyIdentifier;
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
		final StringBuilder sb = new StringBuilder();
		sb.append("PropertyDeclaration for " + ((propertyDescriptor!=null)
					? propertyDescriptor.getQualifiedName().toURI() : ""));
		sb.append(" " + cardinality.toString());
		sb.append(", " + typeDefinition);
		if(null!=constraints){
			Iterator<Constraint> i = constraints.iterator();
			while(i.hasNext()){
				sb.append(" "+i.next().toString());
			}
		}
		return sb.toString();
	}

	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Deprecated
	@Override
	public QualifiedName getQualifiedPropertyIdentifier() {
		if (this.oldIdentifier == null) {
			return null;
		}
		if (!(QualifiedName.isUri(this.oldIdentifier) || QualifiedName
				.isQname(this.oldIdentifier))) {
			return new QualifiedName(VoidNamespace.getInstance(),
					this.oldIdentifier);
		} else {
			return new QualifiedName(this.oldIdentifier);
		}
	}
	

	/**
	 * Sets the property identifier.
	 * @param identifier -
	 */
	public void setPropertyIdentifier(final String identifier) {
		this.oldIdentifier = identifier;
		//TODO This must form a valid URI
		if(!(QualifiedName.isUri(this.oldIdentifier) || QualifiedName.isQname(this.oldIdentifier))){
			this.oldIdentifier =  new QualifiedName(VoidNamespace.getInstance(),this.oldIdentifier).toURI();
		}
	}

}
