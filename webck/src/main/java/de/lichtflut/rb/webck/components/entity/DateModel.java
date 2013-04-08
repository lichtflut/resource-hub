/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.nodes.SNValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(DateModel.class);

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
		Date date = null;
		if(null == value || null == value.getObject()){
			return date;
		}
		// If date was stored as a string, try arastrejus default date format
		ElementaryDataType dataType = ((SNValue)value.getFieldValue().getValue()).getDataType();
		if(ElementaryDataType.STRING.name().equals(dataType.name())){
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(((SNValue)value.getFieldValue().getValue()).getStringValue());
			} catch (ParseException e) {
				LOGGER.error("Could not parse date using format: yyyy-MM-dd. Will attempt another format");
			}
			try {
				date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(((SNValue)value.getFieldValue().getValue()).getStringValue());
			} catch (ParseException e) {
				LOGGER.error("Could not parse date using format:yyyy-MM-dd'T'HH:mm:ssZ.");
			}
		}else{
			Date timeValue = ((SNValue)value.getFieldValue().getValue()).getTimeValue();
			date = new Date(timeValue.getTime());
		}
		return date;
	}

	@Override
	public void setObject(final Date object) {
		value.setObject(object);
	}

}
