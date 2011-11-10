/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.util;

import java.io.Serializable;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.schema.model.LabelBuilder;
import de.lichtflut.rb.web.WSConstants;

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
@SuppressWarnings("serial")
public final class StaticLabelBuilders implements Serializable {

	/**
	 * Private Constructor.
	 */
	private StaticLabelBuilders(){}
	
	// -----------------------------------------------------

	/**
	 * Get the label builder for persons.
	 * @return The person label builder.
	 */
	public static LabelBuilder forPerson() {
		return new LabelBuilder() {
			@Override
			public String build(final RBEntity entity) {
				return getLabel(entity, WSConstants.HAS_FORENAME, WSConstants.HAS_SURNAME);
			}
			@Override
			public String getExpression() {
				return null;
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
			public String build(final RBEntity entity) {
				return getLabel(entity, WSConstants.HAS_ORGA_NAME);
			}
			@Override
			public String getExpression() {
				return null;
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
			public String build(final RBEntity entity) {
				return getLabel(entity, WSConstants.HAS_STREET, WSConstants.HAS_HOUSNR, WSConstants.HAS_CITY);
			}
			@Override
			public String getExpression() {
				return null;
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
			public String build(final RBEntity entity) {
				return entity.toString();
			}
			@Override
			public String getExpression() {
				return null;
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
			public String build(final RBEntity entity) {
				return getLabel(entity, WSConstants.HAS_ZIPCODE, WSConstants.HAS_CITY, WSConstants.HAS_COUNTRY);
			}
			@Override
			public String getExpression() {
				return null;
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
	private static String getLabel(final RBEntity entity, final ResourceID... predicates) {
		final StringBuilder sb = new StringBuilder();
		for(ResourceID predicate : predicates) {
			append(sb, entity, predicate);
		}
		if(sb.length() > 0){
			return sb.substring(0, sb.length()-2);
		}
		return sb.toString();
	}

	/**
	 * TODO: DESCRIPTION.
	 * @param sb -
	 * @param entity -
	 * @param predicate -
	 */
	private static void append(final StringBuilder sb, final RBEntity entity, final ResourceID predicate) {
		final RBField field = entity.getField(predicate);
		if (field == null) {
			sb.append("<" + predicate + "> ");
			return;
		}
		for (Object value : field.getValues()) {
			if((field.isResourceReference()) && (value != null)){
				sb.append(getResourceLabel(value));
			}else{
				sb.append(value);
			}
			sb.append(", ");
		}
	}
	
	private static String getResourceLabel(final Object ref) {
		if (ref instanceof RBEntity) {
			return ((RBEntity) ref).getLabel();
		} else if (ref instanceof ResourceID) {
			return ((ResourceID) ref).getQualifiedName().toURI();
		} else {
			throw new IllegalStateException("Unecpected class for resource reference: " + ref.getClass());
		}
	}
}
