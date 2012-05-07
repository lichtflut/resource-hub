/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Representation of an RB domain.
 * </p>
 *
 * <p>
 * 	Created May 4, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBDomain {
	
	private final ResourceID id;
	
	private String name;
	
	private String title;
	
	private String description;

	// ----------------------------------------------------
	
	/**
	 * @param id The unique ID/URI of the domain. 
	 */
	public RBDomain(ResourceID id) {
		this.id = id;
	}
	
	/**
	 * @param id The unique ID/URI of the domain. 
	 */
	public RBDomain(QualifiedName qn) {
		this(new SimpleResourceID(qn));
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the id
	 */
	public ResourceID getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "RBDomain [id=" + id + ", name=" + name + "]";
	}

}
