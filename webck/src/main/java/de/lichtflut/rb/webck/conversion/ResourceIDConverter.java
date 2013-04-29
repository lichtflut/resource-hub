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

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.convert.IConverter;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import java.util.Locale;

/**
 * Converter for {@link ResourceID}s.
 *
 * Created: Aug 25, 2011
 *
 * @author Ravi Knox
 */
public class ResourceIDConverter implements IConverter<ResourceID> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID convertToObject(final String value, final Locale locale) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return new SimpleResourceID(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String convertToString(final ResourceID ref, final Locale locale) {
		return ref.getQualifiedName().toURI();
	}

}
