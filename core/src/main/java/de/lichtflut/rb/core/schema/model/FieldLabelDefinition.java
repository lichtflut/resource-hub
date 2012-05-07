/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

/**
 * <p>
 *  Definition of the label of a schema field.
 * </p>
 *
 * <p>
 * 	Created Nov 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface FieldLabelDefinition extends Serializable {

	/**
	 * @return the default label for this Field label.
	 */
	String getDefaultLabel();
	
	/**
	 * @param locale
	 * @return the Label for a given {@link Locale}. If no label is set for the given Locale, the Default label will be returned.
	 */
	String getLabel(Locale locale);
	
	/**
	 * @return a {@link Set} of {@link Locale}s that are supported by this Field label.
	 */
	Set<Locale> getSupportedLocales();
	
	/**
	 * Sets the default label.
	 * @param label
	 */
	void setDefaultLabel(String label);
	
	/**
	 * Sets the label for a given {@link Locale}.
	 * @param locale
	 * @param label
	 */
	void setLabel(Locale locale, String label);
	
}
