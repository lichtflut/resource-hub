/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 * Implementation of {@link TypeDefinition}.
 *
 * This implementation tries to give you a perfect way of freedom to decide how to construct and build an object of this class.
 * Therefore this Class has a whole big chunk of constructors and setter/getter-members. Check it out!
 *
 * Please note:
 * This class is flagged as final.
 * That means: If you're willing to remove it to get ability to inherit or sth. like that, please be absolutely sure,
 * that you know everything about this class when you want to override some members e.g. (See the test cases)
 * <p>
 * 	Created Apr 14, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
public final class TypeDefinitionImpl implements TypeDefinition, Serializable {

	private ResourceID id;

	private Set<Constraint> constraints = new HashSet<Constraint>();
	
	private Datatype type =  Datatype.STRING;
	
	private boolean isPublicType = false;
	
	private String name;

	// -----------------------------------------------------
	
	/**
	 * Default constructor. For non public type definitions without ID.
	 */
	public TypeDefinitionImpl() {
		this(new SimpleResourceID(), false);
	}

	/**
	 * Constructor.
	 * @param id The unique technical ID.
	 * @param isPublic Flag for public/private types.
	 */
	public TypeDefinitionImpl(final ResourceID id, final boolean isPublic) {
		this.id = id;
		this.name = id.getQualifiedName().getSimpleName();
		this.isPublicType = isPublic;
	}

	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		return id;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPublicTypeDef() {
		return isPublicType;
	};
	
	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Constraint> getConstraints() {
		if (constraints == null) {
			return Collections.emptySet();
		}
		return constraints;
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	public void addConstraint(final Constraint constraint) {
		constraints.add(constraint);
	}

	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Datatype getDataType() {
		return type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDataType(final Datatype type) {
		this.type = type;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLiteralValue() {
		return !isResourceReference();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResourceReference() {
		return this.type == Datatype.RESOURCE;
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setConstraints(final Collection<Constraint> constraints) {
		this.constraints = new HashSet<Constraint>(constraints);
	}

	// -----------------------------------------------------

	/**
	 * @return the unqualified name of the internal resourceID
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @param name The name.
	 */
	public TypeDefinitionImpl setName(final String name){
		this.name = name;
		return this;
	}

	// ---------------------------------------------------

	/**
	 * @return the qualified name of the internal resourceID
	 */
	public String getIdentifierString(){
		return getID().getQualifiedName().toURI();
	}

	/**
	 * <p>
	 * Tries to generate an URI from the given String, if not, the default Namespace will be used
	 * This method is equivalent to {@link setName(String name)}.
	 * </p>
	 * @param identifierString -
	 */
	public void setIdentifier(final String identifierString){
		setName(identifierString);
	}

	// ---------------------------------------------------

	/**
	 * Just override the toString()-Method.
	 * @return String
	 */
	public String toString(){
		final StringBuilder sb = new StringBuilder();
		if (isPublicType){
			sb.append("public TypeDefinition " + getID());
		} else {
			sb.append("private TypeDefinition");
		}
		sb.append(" " + getDataType());
		if(!constraints.isEmpty()){
			sb.append(" constraints: ");
			final Iterator<Constraint> i = constraints.iterator();
			while(i.hasNext()){
				sb.append(i.next().toString() + " ");
			}
		}
		return sb.toString();
	}
	
	//------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj){
		if(!(obj instanceof TypeDefinition)){
			return false;
		}
		return Infra.equals(this.id, ((TypeDefinition) obj).getID());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	
}
