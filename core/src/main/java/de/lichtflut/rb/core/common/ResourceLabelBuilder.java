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
package de.lichtflut.rb.core.common;

import java.util.Locale;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.ValueNode;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.RBSystem;

/**
 * <p>
 *  Builds localized labels for resources.
 * </p>
 *
 * <p>
 * 	Created Nov 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceLabelBuilder {
	
	private static final ResourceLabelBuilder DEFAULT_INSTANCE = new ResourceLabelBuilder();
	
	// ----------------------------------------------------

	public static ResourceLabelBuilder getInstance() {
		return DEFAULT_INSTANCE;
	}
	
	// ----------------------------------------------------

	public String getFieldLabel(final ResourceID predicate, final Locale locale) {
		final String label = getLabel(predicate, RBSystem.HAS_FIELD_LABEL, locale);
		if (label != null) {
			return label;
		}
		return getLabel(predicate, locale);
	}
	
	public String getLabel(final ResourceID resource, final Locale locale) {
		String label = getLabel(resource, RDFS.LABEL, locale);
		if (label == null && resource != null) {
			label = resource.getQualifiedName().getSimpleName();
		}
		return label;
	}
	
	// ----------------------------------------------------
	
	private String getLabel(final ResourceID src, final ResourceID predicate, final Locale locale) {
		if (predicate == null || src == null) {
			return null;
		} else if (locale == null) { 
			return fetchFirst(src.asResource(), predicate);
		} else {
			return fetchBest(src.asResource(), predicate, locale);
		}
	}

	protected String fetchBest(final ResourceNode src, final ResourceID predicate, final Locale locale) {
		String first = null;
		String noLanguage = null;
		String matchingLanguage = null;
		for (Statement statement : SNOPS.associations(src, predicate)) {
			if (statement.getObject().isValueNode()) {
				final ValueNode value = statement.getObject().asValue();
				if (Infra.equals(value.getLocale(), locale)) {
					return value.getStringValue();
				} else if (matchingLanguage == null && value.getLocale() != null && Infra.equals(locale.getLanguage(), value.getLocale().getLanguage())) {
					matchingLanguage = value.getStringValue();
				} else if (noLanguage == null && value.getLocale() == null) {
					noLanguage = value.getStringValue();
				}else if (first == null) {
					first = value.getStringValue();
				}
			}
		}
		return Infra.coalesce(matchingLanguage, noLanguage, first);
	}

	protected String fetchFirst(final ResourceNode src, final ResourceID predicate) {
		SemanticNode object = SNOPS.fetchObject(src, predicate);
		if (object != null && object.isValueNode()) {
			return object.asValue().getStringValue();
		} else {
			return null;
		}
	}
}
