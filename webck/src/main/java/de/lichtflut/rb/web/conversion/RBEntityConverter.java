/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.conversion;

import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractConverter;

import de.lichtflut.rb.core.schema.model.RBEntity;

/**
 * <p>
 *  Converter for {@link ResourceTypeInstance}.
 * </p>
 *
 * <p>
 * 	Created May 31, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings({ "serial"})
public class RBEntityConverter extends AbstractConverter<RBEntityConverter> {

	/**
	 * Default Constructor.
	 */
	public RBEntityConverter() {
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String, java.util.Locale)
	 */
	/**
	 * @param value /
	 * @param locale /
	 * @return UnsupportedOperationException /
	 */
	public RBEntityConverter convertToObject(final String value, final Locale locale) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param value /
	 * @param locale /
	 * @return /
	 */
	public String convertToString(final RBEntity<Object> value, final Locale locale){
		return value.toString();
	}

	@Override
	protected Class<RBEntityConverter> getTargetType() {
		return RBEntityConverter.class;
	}
}
