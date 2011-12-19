/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryManager;

import de.lichtflut.infra.exceptions.NotYetSupportedException;

/**
 * <p>
 *  General term searcher.
 * </p>
 *
 * <p>
 * 	Created Dec 16, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class TermSearcher {
	
	public enum Mode {
		UNKNOWN,
		URI,
		VALUES,
		ENTITY,
		PROPERTY,
		SUB_CLASS
	}
	
	private static final Pattern ESCAPE_CHARS = Pattern.compile("[\\\\+\\-\\!\\(\\)\\:\\^\\]\\{\\}\\~\\*\\?\\s]");
	
	// ----------------------------------------------------
	
	/**
	 * Search nodes with given term.
	 * @param term The term.
	 * @param type An optional rdf:type criteria. 
	 * @return The query result.
	 */
	public Query prepareQuery(final QueryManager qm, final String term, final Mode mode, final String type) {
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
		case SUB_CLASS:
			query.beginAnd();
			query.beginOr();
			addValues(query, term.split("\\s+"));
			query.addURI(prepareTerm(term, 0.5f));
			query.end();
			query.addField(RDF.TYPE, RDFS.CLASS);
			if (type != null) {
				query.addField(RDFS.SUB_CLASS_OF, type);
			}
			query.end();
			break;
		default:
			throw new NotYetSupportedException();
		}
		return query;
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
	
}
