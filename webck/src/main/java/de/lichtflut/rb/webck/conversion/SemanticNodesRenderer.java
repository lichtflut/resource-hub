/**
 * 
 */
package de.lichtflut.rb.webck.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.IConverterLocator;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.ValueNode;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.common.ResourceLabelBuilder;

/**
 * <p>
 *  Renderer for collections of semantic nodes.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SemanticNodesRenderer implements FieldRenderer<Collection<SemanticNode>> { 

	/** 
	* {@inheritDoc}
	*/
	@Override
	public String render(Collection<SemanticNode> nodes, Locale locale) {
		final List<String> values = new ArrayList<String>();
		for (SemanticNode node: nodes) {
			if (node.isResourceNode()) {
				values.add(render(node.asResource(), locale));
			} else {
				values.add(render(node.asValue(), locale));
			}
		} 
		return join(values, ", ");
	}
	
	// ----------------------------------------------------
	
	public String render(ResourceNode resource, Locale locale) {
		return ResourceLabelBuilder.getInstance().getLabel(resource, locale);
	}
	
	public String render(ValueNode value, Locale locale) {
		final IConverterLocator cl = Application.get().getConverterLocator();
		switch (value.getDataType()) {
		case TIME_OF_DAY:
		case TIMESTAMP:
		case DATE:
			return cl.getConverter(Date.class).convertToString(value.getTimeValue(), locale);
		case DECIMAL:
			return cl.getConverter(BigDecimal.class).convertToString(value.getDecimalValue(), locale);
		case INTEGER:
			return cl.getConverter(BigInteger.class).convertToString(value.getIntegerValue(), locale);
		case BOOLEAN:
		case STRING:
			return value.getStringValue();
		default:
			throw new NotYetImplementedException("Unsupported datatype:" + value.getDataType());
		}
	}
	
	// ----------------------------------------------------
	
	public static String join(final List<String> values, final String sep) {
		final StringBuilder sb = new StringBuilder(128);
		boolean first = true;
		for (Object v : values) {
			if (first) {
				first = false;
			} else {
				sb.append(sep);
			}
			sb.append(v);
		}
		return sb.toString();
	}
	
}
