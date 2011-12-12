/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.organizer;

import org.arastreju.sge.naming.Namespace;

/**
 * <p>
 *  Declaration of a namepspace.
 * </p>
 *
 * <p>
 * 	Created Dec 12, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class NamespaceDeclaration implements Namespace {

	private String uri;
	
	private String prefix;
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public String getUri() {
		return uri;
	}

	/** 
	* {@inheritDoc}
	*/
	@Override
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	// ----------------------------------------------------
	
}
