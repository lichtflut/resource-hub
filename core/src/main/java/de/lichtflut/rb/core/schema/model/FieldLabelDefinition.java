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

	String getDefaultLabel();
	
	String getLabel(Locale locale);
	
	Set<Locale> getSupportedLocales();
	
	void setDefaultLabel(String label);
	
	void setLabel(Locale locale, String label);
	
}
