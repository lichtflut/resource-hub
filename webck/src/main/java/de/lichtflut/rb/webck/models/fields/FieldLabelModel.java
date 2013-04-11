/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.fields;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.webck.common.RBWebSession;

/**
 * <p>
 *  Model for obtaining the locale specific label of an {@link RBField}.
 * </p>
 *
 * <p>
 * 	Created Nov 18, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class FieldLabelModel extends AbstractReadOnlyModel<String> {

	private final IModel<RBField> fieldModel;

	// ----------------------------------------------------

	/**
	 * Constuctor.
	 * @param fieldModel The field model.
	 */
	public FieldLabelModel(final IModel<RBField> fieldModel) {
		this.fieldModel = fieldModel;
	}

	// ----------------------------------------------------

	@Override
	public String getObject() {
		return fieldModel.getObject().getLabel(RBWebSession.get().getLocale());
	}


}
