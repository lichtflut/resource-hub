/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.util.crypt.Base64;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.query.SimpleQueryResult;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.TermSearcher;
import de.lichtflut.rb.webck.common.TermSearcher.Mode;

/**
 * <p>
 *  Query Servlet. Performs a HTTP GET query and returns a list of JSon nodes a result. 
 * </p>
 *
 * <p>
 * 	Created Oct 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceQueryServlet extends HttpServlet {
	
	public final static String QUERY_URI = "/uri";
	
	public final static String QUERY_VALUES = "/values";
	
	public final static String QUERY_ENTITY = "/entity";
	
	public final static String QUERY_PROPERTY = "/property";
	
	public final static String QUERY_SUB_CLASS = "/subclass";
	
	// -- PARAMETERS --------------------------------------
	
	public final static String AUTOCOMPLETE_PARAM = "term";
	
	public final static String TYPE_PARAM = "type";
	
	// ----------------------------------------------------
	
	private Logger logger = LoggerFactory.getLogger(ResourceQueryServlet.class);
	
	// -----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		
		final Mode mode = getMode(req);
		final String term = req.getParameter(AUTOCOMPLETE_PARAM);
		final String type = getTypeConstraint(req);
		
		if (Mode.UNKNOWN == mode || null == term) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			logger.info("quering in mode {} : " + term, mode);
			final QueryResult result = searchNodes(term.trim().toLowerCase(), mode, type);
			autocomplete(result, resp, mode);			
		}
	}

	private String getTypeConstraint(final HttpServletRequest req) {
		final String raw = req.getParameter(TYPE_PARAM);
		if (raw == null) {
			return null;
		} 
		return new String(Base64.decodeBase64(raw));
	}

	// -----------------------------------------------------
	
	/**
	 * @param parameter
	 * @param resp
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	protected void autocomplete(final QueryResult result, final HttpServletResponse resp, final Mode mode)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		final List<JsonNode> jsons = new ArrayList<JsonNode>();
		int counter = 0;
		for (ResourceNode current : result) {
			final String label = ResourceLabelBuilder.getInstance().getLabel(current, resp.getLocale());
			jsons.add(new JsonNode(current, label));
			counter++;
			if (counter > 20) {
				break;
			}
		}
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(resp.getOutputStream(), jsons);
	}

	/**
	 * Search nodes with given term.
	 * @param term The term.
	 * @param type An optional rdf:type criteria. 
	 * @return The query result.
	 * @throws ServletException 
	 */
	protected QueryResult searchNodes(final String term, final Mode mode, final String type) throws ServletException {
		final TermSearcher searcher = new TermSearcher();
		final ModelingConversation conversation = getServiceProvider().getArastejuGate().startConversation();
		final Query query = searcher.prepareQuery(conversation.createQuery(), term, mode, type);
		logger.info("Query: " + query);
		try {
			return query.getResult();
		} catch (RuntimeException e) {
			logger.warn("failed to execute query: " + query.toString());
			return SimpleQueryResult.EMPTY;
		} finally {
			conversation.close();
		}
	}
	
	protected Mode getMode(final HttpServletRequest req) {
		if (QUERY_URI.equals(req.getPathInfo())) {
			return Mode.URI;
		} else if (QUERY_VALUES.equals(req.getPathInfo())) {
			return Mode.VALUES;
		} else if (QUERY_ENTITY.equals(req.getPathInfo())) {
			return Mode.ENTITY;
		} else if (QUERY_PROPERTY.equals(req.getPathInfo())) {
			return Mode.PROPERTY;
		} else if (QUERY_SUB_CLASS.equals(req.getPathInfo())) {
			return Mode.SUB_CLASS;
		} else {
			return Mode.UNKNOWN;
		}
	}
	
	// ----------------------------------------------------
	
	protected ServiceProvider getServiceProvider() throws ServletException {
		final WebApplicationContext wac = 
				WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		final ServiceProvider provider = wac.getBean(ServiceProvider.class);
		if (provider == null || !provider.getContext().isAuthenticated()) {
			throw new ServletException("Unauthenitcated access");
		}
		return provider;
	}
	
	// -----------------------------------------------------
	
	static class JsonNode {
		String id;
		String label;
		String info;
		
		public JsonNode(final ResourceID rid) {
			this(rid, rid.getQualifiedName().toURI());
		}
		
		public JsonNode(final ResourceID rid, final String label) {
			this.id = rid.getQualifiedName().toURI();
			this.label = label;
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
