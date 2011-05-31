/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.conversion;

import java.util.Locale;

import org.apache.wicket.util.convert.converter.AbstractConverter;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;

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
@SuppressWarnings({ "serial", "unchecked" })
public class ResourceTypeInstanceConverter extends AbstractConverter<ResourceTypeInstance> {

	/**
	 * Default Constructor.
	 */
	public ResourceTypeInstanceConverter() {
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String, java.util.Locale)
	 */
	public ResourceTypeInstance<Object> convertToObject(final String value, final Locale locale) {
		throw new UnsupportedOperationException();
	}

	public String convertToString(final ResourceTypeInstance<Object> value, final Locale locale){
		return value.toString();
	}

	@Override
	protected Class<ResourceTypeInstance> getTargetType() {
		return ResourceTypeInstance.class;
	}
	
	


}
