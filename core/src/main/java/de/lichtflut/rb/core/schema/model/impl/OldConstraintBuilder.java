/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;

/**
 * <p>
 * Typical factory to generate some specific instances of {@link Constraint}
 * through class members. Building instances or subclasses from this class is
 * not allowed. Therefore: This factory is specified as final and every
 * constructor is affected to the private modifier
 * </p>
 * 
 * <p>
 * Created Apr 12, 2011
 * </p>
 * 
 * @author Nils Bleisch
 */

public final class OldConstraintBuilder {

	private static final OldConstraintBuilder INSTANCE = new OldConstraintBuilder();

	// -----------------------------------------------------

	/**
	 * <p>
	 * For future uses.
	 * </p>
	 * 
	 * @return the singleton-instance of {@link OldConstraintBuilder}
	 */
	public static OldConstraintBuilder getInstance() {
		return INSTANCE;
	}

	// -----------------------------------------------------

	/**
	 * Builds a Pattern-Constraint.
	 * 
	 * @param literal
	 *            - if this param is null, a blank "" will be chosen instead
	 * @return {@link Constraint}
	 */
	public static Constraint buildLiteralConstraint(String literal) {
		if (literal == null) {
			literal = "";
		}
		return buildConstraint(null, null, literal, null, false);
	}

	/**
	 * Builds a Pattern-Constraint.
	 * 
	 * @param literal
	 *            - if this param is null, a blank "" will be chosen instead
	 * @return {@link Constraint}
	 */
	public static Constraint buildPublicLiteralConstraint(ResourceID id, String name, String literal) {
		if (literal == null) {
			literal = "";
		}
		return buildConstraint(id, name, literal, null, true);
	}

	/**
	 * Builds a Resource-Constraint.
	 * 
	 * @param constraint
	 *            -
	 * @return {@link Constraint}
	 */
	public static Constraint buildResourceConstraint(final ResourceID constraint) {
		return buildConstraint(null, null, null, constraint, false);
	}

	/**
	 * Builds a Resource-Constraint.
	 * 
	 * @param constraint
	 *            -
	 * @return {@link Constraint}
	 */
	public static Constraint buildPublicResourceConstraint(ResourceID id, String name, final ResourceID constraint) {
		return buildConstraint(id, name, null, constraint, true);
	}

	// -----------------------------------------------------

	/**
	 * TODO: PLEASE FIX AND TO COMMMENT! AND This is not the way I like it.
	 * Don't generate thousands of anonymous classes in a running system
	 * instance if you even don't need them. And if You do so, realize my origin
	 * solution as follows!!!: Be a subclass of AbstractConstraint AND(!!!)
	 * override just the method you absolutely need to override: if literal is
	 * not null, override "boolean is LiteralConstraint" with returning true and
	 * so on.... So REMEMBER! THIS IS NOT FINAL, PLEASE CHANGE IT IF YA CAN READ
	 * DIZ. Also just ask you the following: Is a ConstraintFactory even
	 * necessary for all the known use-cases?
	 * 
	 * @param literal
	 *            -
	 * @param resource
	 *            -
	 * @return the constructed Constraint
	 */
	private static Constraint buildConstraint(final ResourceID id, final String name, final String constraint, final ResourceID resource,
			final boolean isPublic) {
		return new AbstractConstraint() {
			
			@Override
			public ResourceID getID() {
				if(id==null){
					return new SimpleResourceID();
				}
				return id;
			}

			public String getLiteralConstraint() {
				return constraint;
			} 

			// -----------------------------------------------------

			public ResourceID getResourceTypeConstraint() {
				return resource;
			}

			// -----------------------------------------------------

			public boolean isLiteralConstraint() {
				return constraint == null ? false : true;
			}

			// -----------------------------------------------------

			public boolean isResourceTypeConstraint() {
				return resource == null ? false : true;
			}

			@Override
			public boolean isPublicConstraint() {
				return isPublic;
			}

			@Override
			public String getName() {
				String displayName = "";
				if (isPublicConstraint()) {
					displayName = getName();
				} else {
					displayName = getNameForPrivateConstraint();
				}
				return displayName;
			}

			private String getNameForPrivateConstraint() {
				String displayName;
				if (isResourceTypeConstraint()) {
					displayName = getResourceTypeConstraint().getQualifiedName().getSimpleName();
				} else {
					displayName = getLiteralConstraint();
				}
				return displayName;
			}

		};
	}

	// -----------------------------------------------------

	/**
	 * Try to hide the constructor, to make this instance not directly
	 * accessible.
	 */
	private OldConstraintBuilder() {
	}

}
