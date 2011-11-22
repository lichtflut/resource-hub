/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.conversion;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.convert.IConverter;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

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
