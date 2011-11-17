/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.io.Serializable;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.LabelBuilder;

/**
 * <p>
 *  Label builder based on an expression.
 * </p>
 *
 * <p>
 * 	Created Nov 10, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ExpressionBasedLabelBuilder implements LabelBuilder, Serializable {

	private final String expression;
	
	private final Element[] elements;
	
	// -----------------------------------------------------
	
	/**
	 * @param expression
	 */
	public ExpressionBasedLabelBuilder(final String expression) {
		this.expression = expression;
		final String[] tokens = expression.split("\\s+");
		this.elements = new Element[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			String current = tokens[i];
			elements[i] = toElement(current);
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String build(final RBEntity entity) {
		final StringBuilder sb = new StringBuilder();
		for (Element el : elements) {
			el.append(entity, sb);
			sb.append(" ");
		}
		return sb.toString().trim();
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getExpression() {
		return expression;
	}
	
	// -----------------------------------------------------
	
	private Element toElement(final String current) {
		if (current.startsWith("<")) {
			if (!current.endsWith(">")) {
				throw new IllegalArgumentException("Invalid token: " + current);
			} else {
				return new LiteralElement(current.substring(1, current.length() -1)); 
			}
		} else {
			return new FieldElement(new SimpleResourceID(current));
		}
	}
	
	// -- ELEMENT TYPES -----------------------------------
	
	interface Element extends Serializable {
		void append(final RBEntity entity, final StringBuilder sb);
	}
	
	class LiteralElement implements Element {
		
		final String value;

		public LiteralElement(String value) {
			this.value = value;
		}

		@Override
		public void append(RBEntity entity, StringBuilder sb) {
			sb.append(value);
		}
		
	}
	
	class FieldElement implements Element {

		final ResourceID predicate;
		
		public FieldElement(final ResourceID predicate) {
			this.predicate = predicate;
		}
		
		@Override
		public void append(RBEntity entity, StringBuilder sb) {
			final RBField field = entity.getField(predicate);
			if (field == null) {
				sb.append("%" + predicate + "% ");
				return;
			}
			boolean first = true;
			for (Object value : field.getValues()) {
				if (value == null) {
					continue;
				}
				if (first) {
					first = false;
				} else {
					sb.append(" ");
				}
				if((field.isResourceReference()) && (value != null)){
					sb.append(getResourceLabel(value));
				} else {
					sb.append(value);
				}
			}
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
