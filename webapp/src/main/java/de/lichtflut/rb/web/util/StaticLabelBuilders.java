/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.util;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.LabelBuilder;
import de.lichtflut.rb.web.metamodel.WSConstants;

/**
 * <p>
 *  Collection of static {@link LabelBuilder}s.
 * </p>
 *
 * <p>
 * 	Created Sep 1, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public final class StaticLabelBuilders {

	/**
	 * Private Constructor.
	 */
	private StaticLabelBuilders(){}

	/**
	 * Get the label builder for persons.
	 * @return The person label builder.
	 */
	public static LabelBuilder forPerson() {
		return new LabelBuilder() {
			@Override
			public String build(final IRBEntity entity) {
				return getLabel(entity, WSConstants.HAS_FORENAME, WSConstants.HAS_SURNAME);
			}
		};
	}

	/**
	 * Get the label builder for organizations.
	 * @return The organization label builder.
	 */
	public static LabelBuilder forOrganization() {
		return new LabelBuilder() {
			@Override
			public String build(final IRBEntity entity) {
				return entity.toString();
			}
		};
	}

	/**
	 * Get the label builder for addresses.
	 * @return The address label builder.
	 */
	public static LabelBuilder forAddress() {
		return new LabelBuilder() {
			@Override
			public String build(final IRBEntity entity) {
				return entity.toString();
			}
		};
	}

	/**
	 * Get the label builder for projects.
	 * @return The project label builder.
	 */
	public static LabelBuilder forProject() {
		return new LabelBuilder() {
			@Override
			public String build(final IRBEntity entity) {
				return entity.toString();
			}
		};
	}

	/**
	 * Get the label builder for cities.
	 * @return The city label builder.
	 */
	public static LabelBuilder forCity() {
		return new LabelBuilder() {
			@Override
			public String build(final IRBEntity entity) {
				return entity.toString();
			}
		};
	}

	// -----------------------------------------------------

	/**
	 * TODO: DESCRIPTION.
	 * @param entity -
	 * @param predicates -
	 * @return -
	 */
	private static String getLabel(final IRBEntity entity, final ResourceID... predicates) {
		final StringBuilder sb = new StringBuilder();
		for(ResourceID predicate : predicates) {
			append(sb, entity, predicate);
		}
		return sb.toString().trim();
	}

	/**
	 * TODO: DESCRIPTION.
	 * @param sb -
	 * @param entity -
	 * @param predicate -
	 */
	private static void append(final StringBuilder sb, final IRBEntity entity, final ResourceID predicate) {
		final IRBField field = entity.getField(predicate.getQualifiedName().toURI());
		for (Object value : field.getFieldValues()) {
			sb.append(value);
			sb.append(" ");
		}
	}

}
