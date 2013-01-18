/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;

/**
 * <p>
 *  Default implementation of {@link FieldLabelDefinition}.
 * </p>
 *
 * <p>
 * 	Created Nov 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class FieldLabelDefinitionImpl implements FieldLabelDefinition {

	private String defaultLabel;

	private final Map<Locale, String> map = new HashMap<Locale, String>();

	// ----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public FieldLabelDefinitionImpl() { }

	/**
	 * Constructor with default label.
	 * @param defaultLabel The default label.
	 */
	public FieldLabelDefinitionImpl(final String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	/**
	 * Constructor which will use the descriptors simple name as default label.
	 * @param propertyDescriptor The property descriptor.
	 */
	public FieldLabelDefinitionImpl(final ResourceID propertyDescriptor) {
		this(propertyDescriptor.getQualifiedName().getSimpleName());
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDefaultLabel() {
		return defaultLabel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLabel(final Locale locale) {
		if (map.containsKey(locale)) {
			return map.get(locale);
		} else {
			return getDefaultLabel();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Locale> getSupportedLocales() {
		return map.keySet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDefaultLabel(final String label) {
		this.defaultLabel = label;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLabel(final Locale locale, final String label) {
		map.put(locale, label);
	}

	// ------------------------------------------------------


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultLabel == null) ? 0 : defaultLabel.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "FieldLabelDefinitionImpl [defaultLabel=" + defaultLabel + ", map=" + map + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FieldLabelDefinitionImpl)) {
			return false;
		}
		FieldLabelDefinitionImpl other = (FieldLabelDefinitionImpl) obj;
		if (defaultLabel == null) {
			if (other.defaultLabel != null) {
				return false;
			}
		} else if (!defaultLabel.equals(other.defaultLabel)) {
			return false;
		}
		if (map == null) {
			if (other.map != null) {
				return false;
			}
		} else if (!map.equals(other.map)) {
			return false;
		}
		return true;
	}

}
