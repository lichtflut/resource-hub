/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

/**
 * <p>
 *  Builder for labels based on a resource's attributes.
 * </p>
 *
 * <p>
 * 	Created Aug 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface LabelBuilder {

	LabelBuilder DEFAULT = new DefaultBuilder();

	/**
	 * Builds a label for the given entity.
	 * @param entity The entity to be labeled.
	 * @return The entitie's label.
	 */
	String build(IRBEntity entity);

	/**
	 *
	 * [TODO Insert description here.
	 */
	public static class DefaultBuilder implements LabelBuilder {
		@Override
		public String build(final IRBEntity entity) {
			return entity.toString();
		}
	}

}
