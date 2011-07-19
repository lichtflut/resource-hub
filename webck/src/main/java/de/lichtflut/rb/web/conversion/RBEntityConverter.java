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
@SuppressWarnings({ "serial", "rawtypes"})
public class RBEntityConverter extends AbstractConverter<RBEntity> {

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
	public RBEntity<Object> convertToObject(final String value, final Locale locale) {
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
	protected Class<RBEntity> getTargetType() {
		return RBEntity.class;
	}
}
