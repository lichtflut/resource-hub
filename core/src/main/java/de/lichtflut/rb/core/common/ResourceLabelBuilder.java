/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.common;

import java.util.Locale;

import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
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
		}
		String first = null;
		String noLanguage = null;
		String matchingLanguage = null;
		for (Statement statement : src.asResource().getAssociations(predicate)) {
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
}
