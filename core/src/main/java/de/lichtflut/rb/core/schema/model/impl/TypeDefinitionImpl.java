/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.naming.VoidNamespace;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.TypeDefinition;

/**
 *  Implementation of {@link TypeDefinition}.
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
public final class TypeDefinitionImpl implements TypeDefinition{

	private static final long serialVersionUID = -863599762328529038L;
	//Instance members
	private Set<Constraint> constraints = new HashSet<Constraint>();
	private ElementaryDataType type =  ElementaryDataType.STRING;
	//private ElementaryDataType type =  ElementaryDataType.UNDEFINED;
	private ResourceID identifier;

	// -----------------------------------------------------
	//Constructor
	/**
	 * Constructor-Party!!!. Readability is not given, I know.
	 * But this isn't really necessary because every possible constructor combination is realized here without any special behavior.
	 */
	public TypeDefinitionImpl(){}
	/**
	 * Constructor.
	 * @param identifierString -
	 */
	public TypeDefinitionImpl(final String identifierString){setName(identifierString);}
	/**
	 * Constructor.
	 * @param type -
	 */
	public TypeDefinitionImpl(final ElementaryDataType type){setElementaryDataType(type);}
	/**
	 * Constructor.
	 * @param constraints -
	 */
	public TypeDefinitionImpl(final Set<Constraint> constraints){setConstraints(constraints);}
	/**
	 * Constructor.
	 * @param identifierString -
	 * @param constraints -
	 */
	public TypeDefinitionImpl(final String identifierString,final Set<Constraint>constraints){
									setName(identifierString);setConstraints(constraints);}
	/**
	 * Constructor.
	 * @param identifierString -
	 * @param type -
	 */
	public TypeDefinitionImpl(final String identifierString,final ElementaryDataType type){
									setName(identifierString);setElementaryDataType(type);}
	/**
	 * Constructor.
	 * @param constraints -
	 * @param type -
	 */
	public TypeDefinitionImpl(final Set<Constraint>constraints,final ElementaryDataType type){
									setConstraints(constraints);setElementaryDataType(type);}
	/**
	 * Constructor.
	 * @param identifierString -
	 * @param constraints -
	 * @param type -
	 */
	public TypeDefinitionImpl(final String identifierString,final Set<Constraint>constraints,final ElementaryDataType type){
						setName(identifierString);setConstraints(constraints);setElementaryDataType(type);}

	// -----------------------------------------------------
	/**
	 * Add a further constraint to the set of constraints. If the set of constraints is null,
	 * setConsraints will be called inside off addConstraint with a new one element sized collection as param including the constraint.
	 * @param constraint -
	 */
	public void addConstraint(final Constraint constraint) {
		if(constraints!=null){
				constraints.add(constraint);
		}else{
			Set<Constraint> constraints = new HashSet<Constraint>();
			constraints.add(constraint);
			setConstraints(constraints);
		}
	}

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

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ElementaryDataType getElementaryDataType() {
		return type;
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		return identifier;
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCustom() {
		return ((!isElementary()) && (!isResourceReference()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isElementary() {
		for (ElementaryDataType elementaryDataType :  ELEMENTATY_DATA_TYPES) {
			if(this.type == elementaryDataType){
				return true;
			}
		}
		return false;
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
		return this.identifier.equals(((TypeDefinition) obj).getID());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode(){
		return  super.hashCode();
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResourceReference() {
		return this.type == ElementaryDataType.RESOURCE;
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValue() {
		return !isResourceReference();
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setConstraints(final Set<Constraint> constraints) {
		this.constraints = constraints;
	}

	// -----------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setElementaryDataType(final ElementaryDataType type) {
		this.type = type;
	}

	// -----------------------------------------------------

	/**
	 * @return the unqualified name of the internal resourceID
	 */
	public String getName(){
		return getID().getName();
	}

	// ---------------------------------------------------

	/**
	 * @return the qualified name of the internal resourceID
	 */
	public String getIdentifierString(){
		return getID().getQualifiedName().toURI();
	}

	// ---------------------------------------------------

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
	 * <p>
	 * Tries to generate an URI from the given String, if not, the default Namespace will be used.
	 * This method is equivalent to {@link setName(String name)}
	 * </p>
	 * @param identifierString -
	 */
	public void setName(final String identifierString){
		if(!(QualifiedName.isUri(identifierString))){
			this.identifier = new SimpleResourceID(VoidNamespace.getInstance(),identifierString);
		}else{
			this.identifier = new SimpleResourceID(new QualifiedName(identifierString));
		}
	}

	/**
	 * Just override the toString()-Method.
	 * @return String
	 */
	public String toString(){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("Identifier: " + getID());
		sBuffer.append("\nDatatype: "+ getElementaryDataType().toString());
		if(null!=constraints){
			Iterator<Constraint> i = constraints.iterator();
			while(i.hasNext()){
				sBuffer.append("\n  - Constraint: "+i.next().toString());
			}
		}
		return sBuffer.toString();
	}
}