/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.config;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.crypt.Base64;

/**
 * <p>
 *  Default path builder to query services.
 * </p>
 *
 * <p>
 * 	Created Jun 22, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class DefaultQueryServicePathBuilder implements QueryServicePathBuilder {
	
	public String queryEntities(String domain, String type) {
		final String ctx = RequestCycle.get().getRequest().getContextPath();
		final StringBuilder sb = new StringBuilder(ctx + "/service/query");
		sb.append("/domains/" + domain);
		sb.append("/entities");
		if (type != null) {
			sb.append("?type=");
			sb.append(Base64.encodeBase64URLSafeString(type.getBytes()));
		}
		return sb.toString();
	}
	
}
