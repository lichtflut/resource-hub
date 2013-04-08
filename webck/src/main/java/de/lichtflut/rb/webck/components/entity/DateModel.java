/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import java.util.Date;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.nodes.SNValue;

import de.lichtflut.rb.webck.conversion.RBDateConverter;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;

/**
 * <p>
 * Implementation of {@link IModel}.
 * Activates {@link RBDateConverter} for InputFields.
 * </p>
 * Created: Apr 8, 2013
 *
 * @author Ravi Knox
 */
public class DateModel implements IModel<Date> {

	private final RBFieldValueModel value;

	// ---------------- Constructor -------------------------

	public DateModel(final RBFieldValueModel model){
		this.value = model;

	}

	// ------------------------------------------------------

	@Override
	public void detach() {
	}

	@Override
	public Date getObject() {
		if(null == value || null == value.getObject()){
			return null;
		}
		Date timeValue = ((SNValue)value.getFieldValue().getValue()).getTimeValue();
		return new Date(timeValue.getTime());
	}

	@Override
	public void setObject(final Date object) {
		value.setObject(object);
	}

}
