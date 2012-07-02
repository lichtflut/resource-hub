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

    String queryResources(String domain, String type);

	String queryEntities(String domain, String type);

    String queryClasses(String domain, String superClass);

    String queryProperties(String domain, String superProperty);

    String queryUsers(String domain);

}
