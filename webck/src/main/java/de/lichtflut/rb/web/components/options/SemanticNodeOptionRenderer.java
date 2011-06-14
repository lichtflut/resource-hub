/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.options;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created May 30, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SemanticNodeOptionRenderer implements IChoiceRenderer<ResourceID> {

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getDisplayValue(java.lang.Object)
	 */
	public Object getDisplayValue(final ResourceID resource) {
		return resource.toString();
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getIdValue(java.lang.Object, int)
	 */
	public String getIdValue(final ResourceID resource, int index) {
		return resource.getQualifiedName().toURI();
	}

}
