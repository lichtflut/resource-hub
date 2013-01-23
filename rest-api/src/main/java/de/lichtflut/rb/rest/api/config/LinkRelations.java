/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api.config;

/**
 * <p>
 *  TODO: To document
 * </p>
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created Jan 17, 2013
 */
public class LinkRelations {

	public static final String BASE_URI = "http://rb.lichtflut.de/rels/";
	
	public static final String SELF_REL = BASE_URI + "self";
	
	public static final String GET_ENTITIES_REL = BASE_URI + "entities/list";
	
	public static final String GET_ENTITY_REL = BASE_URI + "entitiy/show";
	
	public static final String CREATE_AUTH_TOKEN_REL = BASE_URI + "token/create";
	
	public static final String GET_DOMAINS_REL = BASE_URI + "domains/list";
	
}
