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

import de.lichtflut.rb.core.common.EntityLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.AbstractConverter;

import java.util.Locale;

/**
 * <p>
 *  Converter for {@link EntityLabelBuilder}
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class LabelBuilderConverter extends AbstractConverter<EntityLabelBuilder> implements IConverter<EntityLabelBuilder> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityLabelBuilder convertToObject(final String value, final Locale locale) {
		if (StringUtils.isBlank(value)) {
			return EntityLabelBuilder.DEFAULT;
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
	public String convertToString(final EntityLabelBuilder builder, final Locale locale) {
		return builder.getExpression();
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	protected Class<EntityLabelBuilder> getTargetType() {
		return EntityLabelBuilder.class;
	}

}
