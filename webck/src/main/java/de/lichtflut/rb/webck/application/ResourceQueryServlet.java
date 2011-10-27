/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.QueryManager;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.exceptions.NotYetImplementedException;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Oct 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ResourceQueryServlet extends HttpServlet {
	
	public final static String AUTOCOMPLETE_PARAM = "term";
	
	public final static String QUERY_PARAM = "query";
	
	private Logger logger = LoggerFactory.getLogger(ResourceQueryServlet.class);
	
	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameterMap().containsKey(AUTOCOMPLETE_PARAM)) {
			autocomplete(req.getParameter(AUTOCOMPLETE_PARAM), resp);
		} else if (req.getParameterMap().containsKey(QUERY_PARAM)) {
			throw new NotYetImplementedException();
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	// -----------------------------------------------------
	
	/**
	 * @param parameter
	 * @param resp
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	private void autocomplete(final String term, final HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("query: " + term);
		final List<JsonNode> jsons = new ArrayList<JsonNode>();
		final QueryManager qm = getServiceProvider().getArastejuGate().startConversation().createQueryManager();
		final List<ResourceNode> nodes = qm.findByURI(term + "*");
		for (ResourceNode current : nodes) {
			jsons.add(new JsonNode(current));
			logger.info("matching: " + current.getAssociations());
		}
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(resp.getOutputStream(), jsons);
	}
	
	// -----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();
	
	// -----------------------------------------------------
	
	static class JsonNode {
		String id;
		String label;
		String info;
		
		public JsonNode(ResourceID rid) {
			this.id = rid.getQualifiedName().toURI();
			this.label = rid.getQualifiedName().toURI();
			this.info = rid.getQualifiedName().toURI();
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		
	}

}
