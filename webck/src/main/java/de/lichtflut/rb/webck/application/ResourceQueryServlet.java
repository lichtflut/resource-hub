/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;
import org.apache.wicket.util.crypt.Base64;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryManager;
import org.arastreju.sge.query.QueryResult;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.common.ResourceLabelBuilder;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.common.TermSearcher.Mode;

/**
 * <p>
 *  Query Servlet.
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
	
	public final static String QUERY_ENTITY = "/entity";
	
	public final static String QUERY_PROPERTY = "/property";
	
	public final static String AUTOCOMPLETE_PARAM = "term";
	
	public final static String TYPE_PARAM = "type";
	
	public final static String QUERY_PARAM = "query";
	
	private static final Pattern ESCAPE_CHARS = Pattern.compile("[\\\\+\\-\\!\\(\\)\\:\\^\\]\\{\\}\\~\\*\\?\\s]");
	
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
			if (Mode.ENTITY.equals(mode)) {
				final RBEntity entity = getServiceProvider().getEntityManager().find(current);
				if (entity != null) {
					jsons.add(new JsonNode(entity.getID(), entity.getLabel()));
				}
			} else {
				final String label = ResourceLabelBuilder.getInstance().getLabel(current, resp.getLocale());
				jsons.add(new JsonNode(current, label));
			}
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
	 */
	protected QueryResult searchNodes(final String term, final Mode mode, final String type) {
		final QueryManager qm = getServiceProvider().getArastejuGate().createQueryManager();
		final Query query = qm.buildQuery();
		switch (mode){
		case URI:
			query.addURI(prepareTerm(term));
			break;
		case ENTITY:
		case VALUES:
			query.beginAnd();
			addValues(query, term.split("\\s+"));
			if (type != null) {
				query.addField(RDF.TYPE, type);
			}
			query.end();
			break;
		case PROPERTY:
			query.beginAnd();
			query.beginOr();
			addValues(query, term.split("\\s+"));
			query.addURI(prepareTerm(term, 0.5f));
			query.end();
			query.addField(RDF.TYPE, RDF.PROPERTY);
			query.end();
			break;
		default:
			throw new NotYetSupportedException();
		}
		logger.info("Query: " + query);
		return query.getResult();
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
		} else {
			return Mode.UNKNOWN;
		}
	}
	
	protected void addValues(final Query query, final String... values) {
		Validate.notEmpty(values);
		for (String val : values) {
			query.addValue(prepareTerm(val));
		}
	}
	
	protected String prepareTerm(final String orig, float boost) {
		return prepareTerm(orig) + "^" + boost;
	}
	
	protected String prepareTerm(final String orig) {
		final StringBuilder sb = new StringBuilder(128);
		if (!orig.startsWith("*")) {
			sb.append("*");
		}
		sb.append(ESCAPE_CHARS.matcher(orig).replaceAll("\\\\$0"));
		if (!orig.endsWith("*")) {
			sb.append("*");
		}
		return sb.toString();
	}
	
	// -----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();
	
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
