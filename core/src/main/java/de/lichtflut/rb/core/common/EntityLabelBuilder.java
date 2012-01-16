/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.common;

import java.io.Serializable;
import java.util.Locale;

import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 *  Builder for labels based on an entitie's attributes.
 * </p>
 *
 * <p>
 * 	Created Aug 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface EntityLabelBuilder extends Serializable{

	EntityLabelBuilder DEFAULT = new DefaultBuilder();

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
	
	// ----------------------------------------------------

	/**
	 * A default implementation of this interface.
	 */
	@SuppressWarnings("serial")
	public static class DefaultBuilder implements EntityLabelBuilder, Serializable {
		@Override
		public String build(final RBEntity entity) {
			return ResourceLabelBuilder.getInstance().getLabel(entity.getNode(), Locale.getDefault());
		}
		
		/** 
		 * {@inheritDoc}
		 */
		@Override
		public String getExpression() {
			return null;
		}
		
		/** 
		* {@inheritDoc}
		*/
		@Override
		public String toString() {
			return "----";
		}
	}

}
