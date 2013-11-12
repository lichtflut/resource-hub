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
/**
 * 
 */
package de.lichtflut.rb.webck.conversion;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import org.apache.wicket.Application;
import org.apache.wicket.IConverterLocator;
import org.arastreju.sge.eh.meta.NotYetImplementedException;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.ValueNode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
			if (Boolean.TRUE.equals(value.getBooleanValue())) {
				return "yes";
			} else {
				return "no";
			}
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
