/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.conversion;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;

import de.lichtflut.rb.core.schema.model.LabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;

/**
 * <p>
 *  Converter for {@link LabelBuilder}
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class LabelBuilderConverter extends AbstractConverter<LabelBuilder> implements IConverter<LabelBuilder> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LabelBuilder convertToObject(final String value, final Locale locale) {
		if (StringUtils.isBlank(value)) {
			return LabelBuilder.DEFAULT;
		}
		try {
			return new ExpressionBasedLabelBuilder(value);
		} catch (Exception e) {
			throw newConversionException("Not a valid label expression", value, locale);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String convertToString(final LabelBuilder builder, final Locale locale) {
		return builder.getExpression();
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	protected Class<LabelBuilder> getTargetType() {
		return LabelBuilder.class;
	}

}
