/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.config;

import com.sun.jersey.core.util.Base64;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * <p>
 *  Default id builder to query services.
 * </p>
 *
 * <p>
 * 	Created Jun 22, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class DefaultQueryServicePathBuilder implements QueryServicePathBuilder {

    @Override
	public String queryResources(String domain, String type) {
		final StringBuilder sb = preparePathBuilder(domain);
		sb.append("/resources");
		if (type != null) {
			sb.append("?type=");
			sb.append(encode(type));
		}
		return sb.toString();
	}

    @Override
    public String queryEntities(String domain, String type) {
        return new QueryPath(context(), domain)
                .queryEntities()
                .append("type", type)
                .toURI();
	}

    @Override
    public String queryClasses(String domain, String superClass) {
        final StringBuilder sb = preparePathBuilder(domain);
        sb.append("/classes");
        if (superClass != null) {
            sb.append("?superclass=");
            sb.append(encode(superClass));
        }
        return sb.toString();
    }

    @Override
    public String queryProperties(String domain, String superProperty) {
        final StringBuilder sb = preparePathBuilder(domain);
        sb.append("/properties");
        if (superProperty != null) {
            sb.append("?superproperty=");
            sb.append(encode(superProperty));
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
        final StringBuilder sb = new StringBuilder(context());
        sb.append("/domains/").append(domain);
        return sb;
    }

    private String context() {
        return RequestCycle.get().getRequest().getContextPath() + "/service/query";
    }

    String encode(String orig) {
        return new String(Base64.encode(orig));
    }


}
