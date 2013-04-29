/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
