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
	public String getLabel(Locale locale) {
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
	public void setDefaultLabel(String label) {
		this.defaultLabel = label;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setLabel(Locale locale, String label) {
		map.put(locale, label);
	}

}
