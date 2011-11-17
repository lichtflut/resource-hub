/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.conversion;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;

import de.lichtflut.rb.core.entity.RBEntity;

/**
 * <p>
 *  Converter for {@link RBEntity}.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBEntityConverter implements IConverter<RBEntity> {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RBEntity convertToObject(final String value, final Locale locale) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String convertToString(final RBEntity entity, final Locale locale) {
		return entity.getLabel();
	}

}
