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
	
	public String queryResources(String domain, String type) {
		final StringBuilder sb = preparePathBuilder(domain);
		sb.append("/resources");
		if (type != null) {
			sb.append("?type=");
			sb.append(encode(type));
		}
		return sb.toString();
	}

    public String queryEntities(String domain, String type) {
		final StringBuilder sb = preparePathBuilder(domain);
		sb.append("/entities");
		if (type != null) {
			sb.append("?type=");
			sb.append(encode(type));
		}
		return sb.toString();
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
        final String ctx = RequestCycle.get().getRequest().getContextPath();
        final StringBuilder sb = new StringBuilder(ctx + "/service/query");
        sb.append("/domains/").append(domain);
        return sb;
    }

    String encode(String orig) {
        return new String(Base64.encode(orig));
    }


}
