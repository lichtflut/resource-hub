/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;

/**
 * A Builder for {@link Constraint}s. <br>
 * Created: May 2, 2012
 * 
 * @author Ravi Knox
 */
public class ConstraintBuilder {

	/**
	 * Builds a Pattern-Constraint.
	 * @param literal - if null, a blank "" will be chosen instead
	 * @return {@link Constraint}
	 */
	public static Constraint buildLiteralConstraint(String literal) {
		if (literal == null) {
			literal = "";
		}
		return buildConstraint(null, null, literal, null, false);
	}

	/**
	 * Builds a Pattern-Constraint that can be referenced by any {@link PropertyDeclaration}s.
	 * @param id - unique identifier for this Constraint.
	 * @param name - A human readable name to reference this Constraint by
	 * @param literal - if null, a blank "" will be chosen instead
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
	 * @param resource - {@link ResourceID}
	 * @return {@link Constraint}
	 */
	public static Constraint buildResourceConstraint(final ResourceID resource) {
		return buildConstraint(null, null, null, resource, false);
	}

	/**
	 * Builds a Resource-Constraint that can be referenced by any {@link PropertyDeclaration}s.
	 * @param id - unique identifier for this Constraint.
	 * @param name - A human readable name to reference this Constraint by
	 * @param resource -
	 * @return {@link Constraint}
	 */
	public static Constraint buildPublicResourceConstraint(ResourceID id, String name, final ResourceID resource) {
		return buildConstraint(id, name, null, resource, true);
	}

	// ------------------------------------------------------

	private static Constraint buildConstraint(final ResourceID id, final String name, final String pattern,
			final ResourceID resource, final boolean isPublic) {
		return new Constraint() {

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
			public String getName() {
				if (!isPublicConstraint()) {
					return getDisplayName();
				}
				return name;
			}

			private String getDisplayName() {
				if (isResourceTypeConstraint()) {
					return resource.toURI();
				}
				return pattern;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean isResourceTypeConstraint() {
				if (resource != null) {
					return true;
				}
				return false;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getLiteralConstraint() {
				return pattern;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public ResourceID getResourceTypeConstraint() {
				return resource;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean isPublicConstraint() {
				return isPublic;
			}
		};
	}
}
