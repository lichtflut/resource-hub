/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.conversion;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.convert.IConverter;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Converter for {@link QualifiedName}
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class QualifiedNameConverter implements IConverter<QualifiedName> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QualifiedName convertToObject(final String value, final Locale locale) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return new QualifiedName(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String convertToString(final QualifiedName qn, final Locale locale) {
		return qn.toURI();
	}

}
