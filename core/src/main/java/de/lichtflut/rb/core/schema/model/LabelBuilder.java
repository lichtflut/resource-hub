/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;

import de.lichtflut.rb.core.entity.RBEntity;

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
public interface LabelBuilder extends Serializable{

	LabelBuilder DEFAULT = new DefaultBuilder();

	/**
	 * Builds a label for the given entity.
	 * @param entity The entity to be labeled.
	 * @return The entitie's label.
	 */
	String build(RBEntity entity);
	
	/**
	 * Get the expression for building the label.
	 * @return
	 */
	String getExpression();

	/**
	 *
	 * [TODO Insert description here.
	 */
	@SuppressWarnings("serial")
	public static class DefaultBuilder implements LabelBuilder, Serializable {
		@Override
		public String build(final RBEntity entity) {
			return entity.toString();
		}
		
		/** 
		 * {@inheritDoc}
		 */
		@Override
		public String getExpression() {
			return null;
		}
	}

}
