/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis.flowchart;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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
		
		writeLanes(g, model.getLanes());
		writeNodeset(g, model.getNodes());
		writePredecessors(g, model.getNodes());
		
		g.flush(); 
	}
	
	// ----------------------------------------------------
	
	protected void writeLanes(JsonGenerator g, List<Lane> lanes) throws JsonGenerationException, IOException {
		g.writeRaw("DataSet.lanes = ");
		g.writeStartObject();
		for (Lane lane : lanes) {
			g.writeFieldName(lane.getID());
			g.writeStartObject();
			g.writeFieldName("uri");
			g.writeString(lane.getURI());
			g.writeFieldName("title");
			g.writeString(lane.getTitle());
			g.writeEndObject();
		}
		g.writeEndObject();
		g.writeRaw(";\n\n");
	}

	protected void writeNodeset(JsonGenerator g, List<FlowChartNode> nodes) throws JsonGenerationException, IOException {
		g.writeRaw("\n\nDataSet.nodeset = ");
		g.writeStartObject();
		for (FlowChartNode node : nodes) {
			g.writeFieldName(node.getId());
			g.writeStartObject();
			
			g.writeFieldName("id");
			g.writeString(node.getResourceNode().toURI());
			
			g.writeFieldName("name");
			g.writeString(ResourceLabelBuilder.getInstance().getLabel(node.getResourceNode(), locale));
			
			g.writeFieldName("title");
			g.writeString(ResourceLabelBuilder.getInstance().getLabel(node.getResourceNode(), locale));
			
			g.writeFieldName("start");
			g.writeNumber(node.getStart());
			
			g.writeFieldName("end");
			g.writeNumber(node.getEnd());
			
			g.writeFieldName("lane");
			g.writeRawValue("DataSet.lanes." + node.getLane().getID());
			g.writeEndObject();
		}
		g.writeEndObject();
		g.writeRaw(";\n\n");
	}
	
	protected void writePredecessors(JsonGenerator g, Collection<FlowChartNode> nodes) 
			throws JsonGenerationException, IOException {
		for(FlowChartNode node : nodes) {
			g.writeRaw("\nDataSet.nodeset." + node.getId() + ".predecessors =" );
			g.writeStartArray();
			for (FlowChartNode predecessor : node.getPredecessors()) {
				g.writeRawValue("DataSet.nodeset." + predecessor.getId());
			}
			g.writeEndArray();
		}
		g.writeRaw(";\n\n");
	}
}