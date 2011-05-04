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
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;

/**
 *  Implementation of {@link PropertyDeclaration}.
 * </p>
 * 
 * <p>
 *  Properties may either be literal properties (literals such as strings, numbers, dates)
 *  or resource references:
 *  
 *  <ol>
 *   <li> literal </li>
 *    	<ol> 
 *    	 <li> elementary (string, integer, decimal, date,...)
 *    	 <li> custom (user defined)
 *      </ol>
 *   <li> resource reference</li>
 *  <ol>
 *  
 * </p>
 * <p>
 * This instance is inititalized with the following set of default values:
 * <ol>
 * <li>type = UNDEFINED</li>>
 * <li>constraints = an empty set </li>
 * </ol> 
 * 
 * 
 * </p>
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
public final class PropertyDeclarationImpl implements PropertyDeclaration{

	//Instance members
	private Set<Constraint> constraints = new HashSet<Constraint>();
	private ElementaryDataType type =  ElementaryDataType.UNDEFINED;
	private ResourceID identifier;
	
	// -----------------------------------------------------
	//Constructor
	/**
	 * Constructor-Party!!!. Readability is not given, I know.
	 * But this isn't really necessary because every possible constructor combination is realized here without any special behavior.
	 */
	public PropertyDeclarationImpl(){}
	public PropertyDeclarationImpl(String identifierString){setName(identifierString);}
	public PropertyDeclarationImpl(ElementaryDataType type){setElementaryDataType(type);}
	public PropertyDeclarationImpl(Set<Constraint> constraints){setConstraints(constraints);}
	public PropertyDeclarationImpl(String identifierString,Set<Constraint>constraints){
									setName(identifierString);setConstraints(constraints);}
	public PropertyDeclarationImpl(String identifierString,ElementaryDataType type){
									setName(identifierString);setElementaryDataType(type);}
	public PropertyDeclarationImpl(Set<Constraint>constraints,ElementaryDataType type){
									setConstraints(constraints);setElementaryDataType(type);}
	public PropertyDeclarationImpl(String identifierString,Set<Constraint>constraints,ElementaryDataType type){
									setName(identifierString);setConstraints(constraints);setElementaryDataType(type);}
	
	// -----------------------------------------------------
	/**
	 * Add a further constraint to the set of constraints. If the set of constraints is null,
	 * setConsraints will be called inside off addConstraint with a new one element sized collection as param including the constraint.
	 */
	public void addConstraint(Constraint constraint) {
		if(constraints!=null){
			constraints.add(constraint);
		}else{
			Set<Constraint> constraints = new HashSet<Constraint>();
			constraints.add(constraint);
			setConstraints(constraints);
		}
	}

	// -----------------------------------------------------
	
	public Set<Constraint> getConstraints() {
		if (constraints == null) {
			return Collections.emptySet();
		}
		return constraints;
	}

	// -----------------------------------------------------
	
	public ElementaryDataType getElementaryDataType() {
		return type;
	}

	// -----------------------------------------------------
	
	public ResourceID getIdentifier() {
		return identifier;
	}

	// -----------------------------------------------------
	
	public boolean isCustom() {
		return ((!isElementary()) && (!isResourceReference()));
	}


	public boolean isElementary() {
		for (ElementaryDataType elementaryDataType :  ELEMENTATY_DATA_TYPES) {
			if(this.type == elementaryDataType) return true;
		}
		return false;
	}
	
	//------------------------------------------------------
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof PropertyDeclaration)) return false;
		return this.identifier.equals(((PropertyDeclaration) obj).getIdentifier());
	}
	
	
	
	// -----------------------------------------------------

	public boolean isResourceReference() {
		return this.type == ElementaryDataType.RESOURCE;
	}

	// -----------------------------------------------------
	
	public boolean isValue() {
		return !isResourceReference();
	}

	// -----------------------------------------------------
	
	public void setConstraints(Set<Constraint> constraints) {
		this.constraints = constraints;	
	}

	// -----------------------------------------------------
	
	public void setElementaryDataType(ElementaryDataType type) {
		this.type = type;
		
	}

	// -----------------------------------------------------
	
	/**
	 * @return the unqualified name of the internal resourceID
	 */
	public String getName(){
		return getIdentifier().getName();
	}
	
	// ---------------------------------------------------
	
	/**
	 * @return the qualified name of the internal resourceID
	 */
	public String getIdentifierString(){
		return getIdentifier().getQualifiedName().toURI();
	}
	
	// ---------------------------------------------------
	
	
	
	/**
	 * <p>
	 * Tries to generate an URI from the given String, if not, the default Namespace will be used
	 * This method is equivalent to {@link setName(String name)}
	 * </p>
	 */
	public void setIdentifier(String identifierString){
		setName(identifierString);
	}
	
	// ---------------------------------------------------
	
	
	
	
	/**
	 * <p>
	 * Tries to generate an URI from the given String, if not, the default Namespace will be used
	 * This method is equivalent to {@link setName(String name)}
	 * </p>
	 */
	public void setName(String identifierString){
		
		if(!(QualifiedName.isUri(identifierString) || QualifiedName.isUri(identifierString)))	
			this.identifier = new SimpleResourceID(VoidNamespace.getInstance(),identifierString);
		else{
			this.identifier = new SimpleResourceID(new QualifiedName(identifierString));
		}
	}

	/**
	 * Just override the toString()-Method 
	 */
	public String toString(){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("Identifier: " + (this.getIdentifierString()!=null ? getIdentifierString() : "") );
		sBuffer.append("\nDatatype: "+ getElementaryDataType().toString());
		if(null!=constraints){
			Iterator<Constraint> i = constraints.iterator();
			while(i.hasNext())
				sBuffer.append("\n  - Constraint: "+i.next().toString());
			}
		return sBuffer.toString();
	}
	
}
