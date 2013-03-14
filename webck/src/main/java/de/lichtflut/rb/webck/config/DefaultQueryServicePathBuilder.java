/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.config;

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
    public QueryPath create(String domain) {
        return new QueryPath(context(), domain);
    }

    // ----------------------------------------------------

    @Override
	public String queryResources(String domain, String type) {
        return create(domain).queryResources()
                .ofType(type)
                .toURI();
	}

    @Override
    public String queryEntities(String domain, String type) {
        return create(domain).queryEntities()
                .ofType(type)
                .toURI();
	}

    @Override
    public String queryClasses(String domain, String superClass) {
        return create(domain).queryClasses()
                .withSuperClass(superClass)
                .toURI();
    }

    @Override
    public String queryProperties(String domain, String superProperty) {
        return create(domain).queryProperties()
                .withSuperProperty(superProperty)
                .toURI();
    }

    @Override
    public String queryUsers(String domain) {
        return create(domain).queryUsers().toURI();
    }

    // ----------------------------------------------------

    private String context() {
        return RequestCycle.get().getRequest().getContextPath() + "/service/query";
    }

}
