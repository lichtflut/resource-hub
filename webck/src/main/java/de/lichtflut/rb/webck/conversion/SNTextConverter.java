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
