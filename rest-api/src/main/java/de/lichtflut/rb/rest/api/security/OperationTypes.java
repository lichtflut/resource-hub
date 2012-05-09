/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.security;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created May 9, 2012
 */
public interface OperationTypes {
	public enum TYPE{
		GRAPH_READ,
		GRAPH_NODE_READ,
		GRAPH_UPDATE,
		DOMAIN_DELETE,
		DOMAIN_CREATE,
	}
}
