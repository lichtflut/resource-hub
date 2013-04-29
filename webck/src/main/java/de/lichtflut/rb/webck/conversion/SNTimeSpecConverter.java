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
package de.lichtflut.rb.webck.conversion;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.string.Strings;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <p>
 *  Converter for {@link SNTimeSpec}.
 * </p>
 *
 * <p>
 * 	Created May 25, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class SNTimeSpecConverter extends AbstractConverter {

	private final TimeMask mask;

	// -----------------------------------------------------

	/**
	 * Default Constructor.
	 */
	public SNTimeSpecConverter() {
		this(TimeMask.TIMESTAMP);
	}

	/**
	 * Default Constructor.
	 * @param mask /
	 */
	public SNTimeSpecConverter(final TimeMask mask) {
		this.mask = mask;
	}

	// -----------------------------------------------------

	/**
	 * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String,Locale)
	 */
	/**
	 * @param value /
	 * @param locale /
	 * @return /
	 */
	public SNTimeSpec convertToObject(final String value, final Locale locale) {
		if (value == null || Strings.isEmpty(value)) {
			return null;
		}
		final Date date = (Date) parse(getDateFormat(locale), value, locale);
		return new SNTimeSpec(date, mask);
	}

	/**
	 * @see org.apache.wicket.util.convert.IConverter#convertToString(Object, java.util.Locale)
	 * @param value /
	 * @param locale /
	 * @return /
	 */
	@Override
	public String convertToString(final Object value, final Locale locale) {
		final DateFormat dateFormat = getDateFormat(locale);
		if (dateFormat != null) {
			return toString((SNTimeSpec) value, dateFormat);
		}
		return value.toString();
	}


	/**
	 * @param locale /
	 * @return Returns the date format.
	 */
	public DateFormat getDateFormat(final Locale locale) {
		Locale tempLocale = locale;
		if (tempLocale == null) {
			tempLocale = Locale.getDefault();
		}
		return DateFormat.getDateInstance(DateFormat.SHORT, locale);
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.util.convert.converter.AbstractConverter#getTargetType()
	 */
	@Override
	protected Class<?> getTargetType() {
		return SNTimeSpec.class;
	}

	/**
	 * @param spec /
	 * @param format /
	 * @return /
	 */
	protected String toString(final SNTimeSpec spec, final DateFormat format) {
		return format.format(spec.getTimeValue());
	}

}
