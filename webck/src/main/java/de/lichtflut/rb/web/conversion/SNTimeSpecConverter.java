/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.conversion;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.apache.wicket.util.string.Strings;
import org.arastreju.sge.model.TimeMask;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;

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
	 */
	public SNTimeSpecConverter(final TimeMask mask) {
		this.mask = mask;
	}
	
	// -----------------------------------------------------

	/**
	 * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String,Locale)
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
	 */
	@Override
	public String convertToString(final Object value, Locale locale) {
		final DateFormat dateFormat = getDateFormat(locale);
		if (dateFormat != null) {
			return toString((SNTimeSpec) value, dateFormat);
		}
		return value.toString();
	}


	/**
	 * @param locale
	 * @return Returns the date format.
	 */
	public DateFormat getDateFormat(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
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
	
	protected String toString(final SNTimeSpec spec, final DateFormat format) {
		return format.format(spec.getTimeValue());
	}

}
