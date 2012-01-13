/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.domains;

import java.io.Serializable;

import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.security.Domain;

/**
 * <p>
 *  Transient domain to be used as model backing a form.
 * </p>
 *
 * <p>
 * 	Created Jan 12, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class TransientDomain implements Domain, Serializable {
	
	private String uniqueName;
	private String title;
	private String description;
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceNode getAssociatedResource() {
		return null;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getUniqueName() {
		return uniqueName;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @param uniqueName the uniqueName to set
	 */
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean isMasterDomain() {
		return false;
	}

}
