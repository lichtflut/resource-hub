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
package de.lichtflut.rb.core.common;

import org.apache.commons.lang3.Validate;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.eh.meta.NotYetSupportedException;
import org.arastreju.sge.query.Query;

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
		VALUES,
		ENTITY,
		PROPERTY,
		SUB_CLASS
	}

    // ----------------------------------------------------

    private final Query query;

    // ----------------------------------------------------

    public TermSearcher(Query query) {
        this.query = query;
    }

    // ----------------------------------------------------
	
	/**
	 * Search nodes with given term.
	 * @param term The term.
	 * @param type An optional rdf:type criteria. 
	 * @return The query result.
	 */
	public Query prepareQuery(final String term, final Mode mode, final String type) {
		switch (mode){
		case ENTITY:
		case VALUES:
			query.beginAnd();
			addValues(term);
			if (type != null) {
				query.addField(RDF.TYPE, type);
			}
			query.end();
			break;
		case PROPERTY:
			query.beginAnd();
			query.beginOr();
				addValues(term);
				query.addURI(prepareTerm(term, 0.5f));
				query.end();
			query.addField(RDF.TYPE, RDF.PROPERTY);
			query.end();
			break;
		case SUB_CLASS:
			query.beginAnd();
			query.beginOr();
				addValues(term);
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
	
	// ----------------------------------------------------

	public void addValues(final String term) {
		if (term.startsWith(SearchTerm.VERBATIM_PREFIX)) {
			query.addValue(term.substring(SearchTerm.VERBATIM_PREFIX.length()));
		} else {
			addValues(term.split("\\s+"));
		}
	}
	
	public void addValues(final String[] values) {
		Validate.notEmpty(values);
		for (String val : values) {
			query.addValue(escape(val));
		}
	}

    // ----------------------------------------------------
	
	protected String prepareTerm(final String orig) {
		if (orig.startsWith(SearchTerm.VERBATIM_PREFIX)) {
			return orig.substring(SearchTerm.VERBATIM_PREFIX.length());
		} else {
			return escape(orig);	
		}
	}
	
	protected String prepareTerm(final String orig, float boost) {
		return prepareTerm(orig) + "^" + boost;
	}
	
	protected String escape(final String orig) {
		final StringBuilder sb = new StringBuilder(128);
		if (!orig.startsWith("*")) {
			sb.append("*");
		}
		sb.append(SearchTerm.ESCAPE_CHARS.matcher(orig).replaceAll("\\\\$0"));
		if (!orig.endsWith("*")) {
			sb.append("*");
		}
		return sb.toString();
	}
	
}
