/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.Namespace;
import org.arastreju.sge.naming.NamespaceHandle;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.common.EntityLabelBuilder;
import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;

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
public class ExpressionBasedLabelBuilder implements EntityLabelBuilder, Serializable {

	private final Element[] elements;

	private final Map<String, NamespaceHandle> namespaces;
	
	// -----------------------------------------------------
	
	/**
     * @param expression The label expression.
	 */
	public ExpressionBasedLabelBuilder(final String expression) throws LabelExpressionParseException {
		this(expression, Collections.<String, NamespaceHandle>emptyMap());
	}
	
	/**
	 * @param expression The label expression.
     * @param namespaces The map of namespaces to be used.
	 */
	public ExpressionBasedLabelBuilder(final String expression, final Map<String, NamespaceHandle> namespaces) 
			throws LabelExpressionParseException {
		this.namespaces = namespaces;
		final String[] tokens = expression.split("\\s+");
		this.elements = new Element[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			String current = tokens[i];
			elements[i] = toElement(current);
		}
	}
	
	// ----------------------------------------------------

	@Override
	public String build(final RBEntity entity) {
		final Locale locale = Locale.getDefault();
		final StringBuilder sb = new StringBuilder();
		boolean hasContent = false;
		for (Element el : elements) {
			hasContent = el.append(entity, sb, locale) || hasContent;
			sb.append(" ");
		}
		if (hasContent) {
			return sb.toString().trim();
		} else {
			return "";
		}
	}

	@Override
	public String getExpression() {
		final StringBuilder sb = new StringBuilder();
		for (Element el : elements) {
			sb.append(" ");
			sb.append(el);
		}
		return sb.toString().trim();
	}
	
	@Override
	public String toString() {
		return getExpression();
	}
	
	// -----------------------------------------------------
	
	private Element toElement(final String current) throws LabelExpressionParseException {
		if (current.startsWith("<")) {
			if (!current.endsWith(">")) {
				throw new LabelExpressionParseException("Invalid token: " + current);
			} else {
				return new LiteralElement(current.substring(1, current.length() -1)); 
			}
		} else {
			return toFieldElement(current);
			
		}
	}
	
	/**
	 * @param name The name of the element.
	 * @return The element.
	 */
	private Element toFieldElement(final String name) throws LabelExpressionParseException {
		if (QualifiedName.isUri(name)) {
			return new FieldElement(new SimpleResourceID(name));
		} else if (QualifiedName.isQname(name)) {
			final String prefix = QualifiedName.getPrefix(name);
			final String simpleName = QualifiedName.getSimpleName(name);
			if (namespaces.containsKey(prefix)) {
				final Namespace namespace = namespaces.get(prefix);
				return new FieldElement(new SimpleResourceID(namespace.getUri(), simpleName));
			} else {
				throw new LabelExpressionParseException("Could not resolve URI for " + name);
			}
		} else {
			throw new LabelExpressionParseException("Field is neither URI nor QName: '" + name + "'");
		}
	}
	
	// -- ELEMENT TYPES -----------------------------------
	
	interface Element extends Serializable {
		boolean append(RBEntity entity, StringBuilder sb, Locale locale);
	}
	
	class LiteralElement implements Element {
		
		final String value;

		public LiteralElement(String value) {
			this.value = value;
		}

		@Override
		public boolean append(RBEntity entity, StringBuilder sb, Locale locale) {
			sb.append(value);
			return false;
		}
		
		/** 
		* {@inheritDoc}
		*/
		@Override
		public String toString() {
			return "<" + value + ">";
		}
		
	}
	
	class FieldElement implements Element {

		final ResourceID predicate;
		
		public FieldElement(final ResourceID predicate) {
			this.predicate = predicate;
		}
		
		@Override
		public boolean append(RBEntity entity, StringBuilder sb, Locale locale) {
			final RBField field = entity.getField(predicate);
			if (field == null) {
				sb.append("%" + predicate + "% ");
				return false;
			} 
			if (field.getValues().isEmpty()) {
				return false;
			}
			boolean first = true;
			for (Object value : field.getValues()) {
				if (first) {
					first = false;
				} else {
					sb.append(" ");
				}
				if((field.isResourceReference()) && (value != null)){
					sb.append(getResourceLabel(value, locale));
				} else {
					sb.append(value);
				}
			}
			return true;
		}
		
		public String toString() {
			return predicate.getQualifiedName().toURI();
		}
		
	}
	
	private static String getResourceLabel(final Object ref, final Locale locale) {
		if (ref instanceof ResourceID) {
			return ResourceLabelBuilder.getInstance().getLabel((ResourceID) ref, locale);
		} else {
			throw new IllegalStateException("Unecpected class for resource reference: " + ref.getClass());
		}
	}

}
