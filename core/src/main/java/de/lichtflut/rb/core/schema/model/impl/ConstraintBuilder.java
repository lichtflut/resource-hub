/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

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

public final class ConstraintBuilder {

	private static final ConstraintBuilder INSTANCE =  new ConstraintBuilder();

	// -----------------------------------------------------

	/**
	 * <p>
	 * For future uses.
	 * </p>
	 * @return the singleton-instance of {@link ConstraintBuilder}
	 */
	public static ConstraintBuilder getInstance(){
		return INSTANCE;
	}

	// -----------------------------------------------------

	/**
	 * Builds a Pattern-Constraint.
	 * @param literal - if this param is null, a blank "" will be chosen instead
	 * @return {@link Constraint}
	 */
	public static Constraint buildLiteralConstraint(String literal){
		if (literal==null){
			literal = "";
		}
		return buildContraintInstanceFromParams(literal, null);
	}

	/**
	 * Builds a Resource-Constraint.
	 * @param constraint -
	 * @return {@link Constraint}
	 */
	public static Constraint buildResourceConstraint(final ResourceID constraint){
		return buildContraintInstanceFromParams(null, constraint);
	}

	// -----------------------------------------------------

	/**
	 * TODO: PLEASE FIX AND TO COMMMENT! AND This is not the way I like it.
	 * Don't generate thousands of anonymous classes in a running system instance if you even don't need them.
	 * And if You do so, realize my origin solution as follows!!!:
	 * Be a subclass of AbstractConstraint AND(!!!) override just the method you absolutely need to override:
	 * if literal is not null, override "boolean is LiteralConstraint" with returning true and so on....
	 * So REMEMBER! THIS IS NOT FINAL, PLEASE CHANGE IT IF YA CAN READ DIZ.
	 * Also just ask you the following: Is a ConstraintFactory even necessary for all the known use-cases?
	 *
	 * @param literal -
	 * @param resource -
	 * @return the constructed Constraint
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



    /**
     * Try to hide the constructor, to make this instance not directly accessible.
     */
    private ConstraintBuilder(){}


}
