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
	
	public String queryResources(String domain, String type) {
		final StringBuilder sb = preparePathBuilder(domain);
		sb.append("/resources");
		if (type != null) {
			sb.append("?type=");
			sb.append(Base64.encodeBase64URLSafeString(type.getBytes()));
		}
		return sb.toString();
	}

    public String queryEntities(String domain, String type) {
		final StringBuilder sb = preparePathBuilder(domain);
		sb.append("/entities");
		if (type != null) {
			sb.append("?type=");
			sb.append(Base64.encodeBase64URLSafeString(type.getBytes()));
		}
		return sb.toString();
	}

    @Override
    public String queryClasses(String domain, String superClass) {
        final StringBuilder sb = preparePathBuilder(domain);
        sb.append("/classes");
        if (superClass != null) {
            sb.append("?superclass=");
            sb.append(Base64.encodeBase64URLSafeString(superClass.getBytes()));
        }
        return sb.toString();
    }

    @Override
    public String queryProperties(String domain, String superProperty) {
        final StringBuilder sb = preparePathBuilder(domain);
        sb.append("/properties");
        if (superProperty != null) {
            sb.append("?superproperty=");
            sb.append(Base64.encodeBase64URLSafeString(superProperty.getBytes()));
        }
        return sb.toString();
    }

    @Override
    public String queryUsers(String domain) {
        final StringBuilder sb = preparePathBuilder(domain);
        sb.append("/users");
        return sb.toString();
    }

    // ----------------------------------------------------

    private StringBuilder preparePathBuilder(String domain) {
        final String ctx = RequestCycle.get().getRequest().getContextPath();
        final StringBuilder sb = new StringBuilder(ctx + "/service/query");
        sb.append("/domains/" + domain);
        return sb;
    }


}
