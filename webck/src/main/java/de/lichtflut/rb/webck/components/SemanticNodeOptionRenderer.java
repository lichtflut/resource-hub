/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created May 30, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class SemanticNodeOptionRenderer implements IChoiceRenderer<ResourceID> {

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getDisplayValue(java.lang.Object)
	 */
	/**
	 * @param resource /
	 * @return /
	 */
	public Object getDisplayValue(final ResourceID resource) {
		return resource.toString();
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getIdValue(java.lang.Object, int)
	 */
	/**
	 * @param resource /
	 * @param index /
	 * @return /
	 */
	public String getIdValue(final ResourceID resource, final int index) {
		return resource.getQualifiedName().toURI();
	}

}
