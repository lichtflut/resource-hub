/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Locale;

import org.arastreju.sge.model.nodes.ResourceNode;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.webck.components.infovis.AbstractJsonStream;

/**
 * <p>
 *  JSON stream based on a flow chart model.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class FlowChartJsonStream extends AbstractJsonStream {

	private final FlowChartModel model;
	private final Locale locale;
	
	// ----------------------------------------------------
	
	/**
	 * @param model
	 */
	public FlowChartJsonStream(final FlowChartModel model, final Locale locale) {
		this.model = model;
		this.locale = locale;
	}

	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void write(OutputStreamWriter writer) throws IOException {
		final JsonGenerator g = new JsonFactory().createJsonGenerator(writer).useDefaultPrettyPrinter();
		g.writeRaw("var DataSet = {};\n\n");
		writeNodeset(g, model.getNodes());
		
		g.flush(); 
	}
	
	// ----------------------------------------------------
	
	protected void writeNodeset(JsonGenerator g, List<ResourceNode> nodes) throws JsonGenerationException, IOException {
		g.writeRaw("DataSet.nodeset = ");
		g.writeStartObject();
		int id = 1;
		for (ResourceNode node : nodes) {
			g.writeFieldName("n" + id++);
			g.writeStartObject();
			g.writeFieldName("id");
			g.writeString(node.toURI());
			g.writeFieldName("name");
			g.writeString(ResourceLabelBuilder.getInstance().getLabel(node, locale));
			g.writeFieldName("title");
			g.writeString(ResourceLabelBuilder.getInstance().getLabel(node, locale));
			g.writeEndObject();
		}
		g.writeEndObject();
	}

}
