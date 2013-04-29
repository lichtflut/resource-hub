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
package de.lichtflut.rb.webck.models;

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
