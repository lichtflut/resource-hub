/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema;

import java.util.Locale;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNProperty;

import de.lichtflut.rb.core.RB;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Nov 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class FieldLabelBuilder {
	
	private static final FieldLabelBuilder DEFAULT_INSTANCE = new FieldLabelBuilder();
	
	// ----------------------------------------------------

	public static FieldLabelBuilder getInstance() {
		return DEFAULT_INSTANCE;
	}
	
	// ----------------------------------------------------

	public String getLabel(final SNProperty property, final Locale locale) {
		String label = getLabel(property, RB.HAS_FIELD_LABEL, locale);
		if (label == null) {
			label = getLabel(property, RDFS.LABEL, locale);
		}
		if (label == null) {
			label = property.getName();
		}
		return label;
	}
	
	// ----------------------------------------------------
	
	private String getLabel(final SNProperty property, final ResourceID predicate, final Locale locale) {
		final SemanticNode label = SNOPS.fetchObject(property, RB.HAS_FIELD_LABEL);
		if (label != null && label.isValueNode()) {
			return label.asValue().getStringValue();
		} else {
			return null;
		}
	}
}
