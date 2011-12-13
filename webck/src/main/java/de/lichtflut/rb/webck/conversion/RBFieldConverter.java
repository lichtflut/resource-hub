/**
 * 
 */
package de.lichtflut.rb.webck.conversion;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.util.convert.IConverter;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.core.entity.RBField;

/**
 * <p>
 *  Converter for RBField. 
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBFieldConverter implements IConverter<RBField> {

	@Override
	public String convertToString(final RBField field, final Locale locale) {
		final IConverterLocator cl = Application.get().getConverterLocator();
		switch (field.getDataType()) {
		case BOOLEAN:
			return join(field.getValues(), cl.getConverter(Boolean.class), locale, ", ");
		case TIME_OF_DAY:
		case TIMESTAMP:
		case DATE:
			return join(field.getValues(), cl.getConverter(Date.class), locale, ", ");
		case DECIMAL:
		case INTEGER:
			return join(field.getValues(), cl.getConverter(Number.class), locale, ", ");
		case RESOURCE:
			return join(field.getValues(), new RBEntityConverter(), locale, ", ");
		case STRING:
		case TEXT:
			return join(field.getValues(), cl.getConverter(String.class), locale, ", ");
			
		default:
			throw new NotYetSupportedException("No converter for :" + field.getDataType());
		}
	}
	
	@Override
	public RBField convertToObject(String value, Locale locale) {
		throw new UnsupportedOperationException();
	}
	
	// ----------------------------------------------------
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String join(final List<Object> values, final IConverter converter, final Locale locale, final String sep) {
		final StringBuilder sb = new StringBuilder(128);
		boolean first = true;
		for (Object v : values) {
			if (first) {
				first = false;
			} else {
				sb.append(sep);
			}
			sb.append(converter.convertToString(v, locale));
		}
		return sb.toString();
	}
	
	private static class RBEntityConverter implements IConverter<RBEntityReference> {

		@Override
		public RBEntityReference convertToObject(String value, Locale locale) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String convertToString(RBEntityReference value, Locale locale) {
			if (value.isResolved()) {
				return value.getEntity().getLabel();	
			} else {
				return value.getQualifiedName().toURI();
			}
			
		}
		
	}
	
}
