/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.common;

import java.util.Locale;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.RB;

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
		final String label = getLabel(predicate, RB.HAS_FIELD_LABEL, locale);
		if (label != null) {
			return label;
		}
		return getLabel(predicate, locale);
	}
	
	public String getLabel(final ResourceID resource, final Locale locale) {
		String label = getLabel(resource, RDFS.LABEL, locale);
		if (label == null) {
			label = resource.getQualifiedName().getSimpleName();
		}
		return label;
	}
	
	// ----------------------------------------------------
	
	private String getLabel(final ResourceID src, final ResourceID predicate, final Locale locale) {
		if (predicate == null || src == null) {
			return null;
		}
		final SemanticNode label = SNOPS.fetchObject(src.asResource(), predicate);
		if (label != null && label.isValueNode()) {
			return label.asValue().getStringValue();
		} else {
			return null;
		}
	}
}
