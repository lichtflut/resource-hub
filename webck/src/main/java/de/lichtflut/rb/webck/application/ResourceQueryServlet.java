/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryManager;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.query.UriParam;
import org.arastreju.sge.query.ValueParam;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
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
	
	public final static String QUERY_URI = "/uri";
	
	public final static String QUERY_VALUES = "/values";
	
	public final static String AUTOCOMPLETE_PARAM = "term";
	
	public final static String QUERY_PARAM = "query";
	
	private Logger logger = LoggerFactory.getLogger(ResourceQueryServlet.class);
	
	// -----------------------------------------------------
	
	enum Mode {
		URI,
		VALUES
	}
	
	// -----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		
		Mode mode = Mode.VALUES; 
		if (QUERY_URI.equals(req.getPathInfo())) {
			mode = Mode.URI;
		}
		
		@SuppressWarnings("rawtypes")
		final Map map = req.getParameterMap();
		if (map.containsKey(AUTOCOMPLETE_PARAM)) {
			autocomplete(req.getParameter(AUTOCOMPLETE_PARAM), resp, mode);
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
	protected void autocomplete(final String term, final HttpServletResponse resp, final Mode mode)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		logger.info("quering in mode {} : " + term, mode);
		final List<JsonNode> jsons = new ArrayList<JsonNode>();
		for (ResourceNode current : searchNodes(term, mode)) {
			jsons.add(new JsonNode(current));
		}
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(resp.getOutputStream(), jsons);
	}

	/**
	 * @param term
	 * @return
	 */
	protected QueryResult searchNodes(final String term, final Mode mode) {
		final QueryManager qm = getServiceProvider().getArastejuGate().startConversation().createQueryManager();
		final Query query = qm.buildQuery();
		switch (mode){
		case URI:
			query.add(new UriParam("*" + term + "*"));
			break;
		case VALUES:
			query.add(new ValueParam("*" + term + "*"));
			break;
		default:
			throw new NotYetSupportedException();
		}
		return query.getResult();
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
