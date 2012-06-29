/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.conversion;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import org.arastreju.sge.model.nodes.views.SNText;

import java.util.Locale;

/**
 * <p>
 *  Converter for {@link SNText}.
 * </p>
 *
 * <p>
 * 	Created May 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class SNTextConverter extends AbstractConverter {

	/**
	 * Default Constructor.
	 */
	public SNTextConverter() {
	}

	/**
	 * @param value /
	 * @param locale /
	 * @return /
	 */
	public Object convertToObject(final String value, final Locale locale) {
		return new SNText(value);
	}

	@Override
	protected Class<?> getTargetType() {
		return SNText.class;
	}

}
