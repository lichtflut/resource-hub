/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;


/**
* <p>
*  Typical factory to generate some specific instances of {@link Constraint} through class members.
*  Building instances or subclasses from this class is not allowed.
*  Therefore: This factory is specified as final and every constructor is affected to the private modifier
* </p>
*
* <p>
* 	Created Apr 12, 2011
* </p>
*
* @author Nils Bleisch
*/

public final class ConstraintFactory{

	
	//Let's instance of this class be a Singleton
	private static ConstraintFactory instance =  new ConstraintFactory();
	
	// -----------------------------------------------------
	
	/*For future uses */
	public static ConstraintFactory getInstance(){
		return instance;
	}
	
	// -----------------------------------------------------
	
	
	/**
	 * Builds a Pattern-Constraint
	 * @param literal, if this param is null, a blank "" will be chosen instead
	 * @return {@link Constraint}
	 */
	public static Constraint buildConstraint(final String literal){
		/** Make sure that literal has to be "" if it's null
		 *  Currently this is done by buildConstraint(final String[] literals)
		 *  A test already to verifies this
		 */
		return buildConstraint(new String[]{literal});
	}
	
	
	/**
	 * Builds a Resource-Constraint
	 * @param constraint
	 * @return {@link Constraint}
	 */
	public static Constraint buildConstraint(final ResourceID constraint){
		return buildContraintInstanceFromParams(null, constraint); 
	}
	
	
	// -----------------------------------------------------
	
	/**
	 * Please note, that this method is for future uses.
	 * All those literals will be concatenated in order to build one whole literal string.
	 * If an element is null, a blank "" will be taken instead
	 * @param literals
	 * @return {@link Constraint}
	 * @throws NullPointerException - if the specified collection is null.
	 */
	public static Constraint buildConstraint(final String[] literals){
		if (literals==null) throw new NullPointerException("literals can not be null");
		//Build one whole literal
		StringBuffer literal = new StringBuffer("");
		for (String string : literals) {
			//If the string is null, append a blank "" instead of "null"
			literal.append(string==null ? "" : string);
		}
		return buildContraintInstanceFromParams(literal.toString(), null); 
	}
	
	// -----------------------------------------------------
	
	/**
	 * Please note, that this method is for future uses.
	 * All those literals will be concatenated in order to build one whole literal string.
	 * If an element is null, a blank "" will be taken instead
	 * @param literals
	 * @return {@link Constraint}
	 * @throws NullPointerException - if the specified collection is null.
	 */
	public static Constraint buildConstraint(final Collection<String> literals){
		if (literals==null) throw new NullPointerException("literals can not be null");
		return buildConstraint(
				(new ArrayList<String>(literals).toArray(new String[literals.size()]))
				);
	}
	
	/**
	 * TODO: PLEASE FIX AND TO COMMMENT! AND This is not the way I like it.
	 * Don't generate thousands of anonymous classes in a running system instance if you even don't need them.
	 * And if You do so, realize my origin solution as follows!!!:
	 * Be a subclass of AbstractConstraint AND(!!!) override just the method you absolutely need to override:
	 * if literal is not null, override "boolean is LiteralConstraint" with returning true and so on....
	 * So REMEMBER! THIS IS NOT FINAL, PLEASE CHANGE IT IF YA CAN READ DIZ.
	 * Also just ask you the following: Is a ConstraintFactory even necessary for all the known use-cases?
	 * 
	 * @param literal
	 * @param resource
	 * @return
	 */
	private static Constraint buildContraintInstanceFromParams(final String literal, final ResourceID resource){
		return new AbstractConstraint(){
			
			public String getLiteralConstraint() {
				return literal;
			}

			// -----------------------------------------------------

			public ResourceID getResourceTypeConstraint() {
				return resource;
			}

			// -----------------------------------------------------
			
			public boolean isLiteralConstraint() {
				return literal==null ? false : true;
			}

			// -----------------------------------------------------
			

			public boolean isResourceTypeConstraint() {
				return resource==null ? false : true;
			}
		};
	}
	
	
	// -----------------------------------------------------
	
	
    //Constructor 
    //Try to hide the constructor, to make this instance not directly accessible
    private ConstraintFactory(){}
	
	
}
