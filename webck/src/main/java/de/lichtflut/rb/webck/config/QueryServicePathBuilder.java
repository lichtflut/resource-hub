/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.config;

/**
 * <p>
 *  Provider for URIs of the query service.
 * </p>
 *
 * <p>
 * 	Created Jun 22, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface QueryServicePathBuilder {

    /**
     * Create a new path for given domain.
     * @param domain The domain.
     * @return The query path (builder object).
     */
    QueryPath create(String domain);

    // ----------------------------------------------------

    @Deprecated
    String queryResources(String domain, String type);

    @Deprecated
	String queryEntities(String domain, String type);

    @Deprecated
    String queryClasses(String domain, String superClass);

    @Deprecated
    String queryProperties(String domain, String superProperty);

    @Deprecated
    String queryUsers(String domain);

}
