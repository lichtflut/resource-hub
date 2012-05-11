/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security;

import java.io.Serializable;

import static org.arastreju.sge.SNOPS.*;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.security.Domain;

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
public class RBDomain implements Serializable {
	
	private final QualifiedName qn;
	
	private String name;
	
	private String title;
	
	private String description;

	// ----------------------------------------------------
	
	/**
	 * Creates a new domain. 
	 */
	public RBDomain() {
		this(new SimpleResourceID().getQualifiedName());
	}
	
	/**
	 * Creates a new domain. 
	 * @param qn The unique ID/URI of the domain. 
	 */
	public RBDomain(QualifiedName qn) {
		this.qn = qn;
	}
	
	/**
	 * Create a domain object based on a domain node.
	 * @param domainNode The node.
	 */
	public RBDomain(ResourceNode domainNode) {
		this.qn = domainNode.getQualifiedName();
		this.name = string(singleObject(domainNode, Aras.HAS_UNIQUE_NAME));
		this.title = string(singleObject(domainNode, Aras.HAS_TITLE));
		this.description = string(singleObject(domainNode, Aras.HAS_DESCRIPTION));
	}
	
	/**
	 * Constructor.
	 * @param qn The unique ID/URI of the domain. 
	 */
	public RBDomain(Domain domain) {
		this.qn = domain.getQualifiedName();
		this.name = domain.getUniqueName();
		this.description = domain.getDescription();
		this.title = domain.getTitle();
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the QualifiedName.
	 */
	public QualifiedName getQualifiedName() {
		return qn;
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
		return "RBDomain [qn=" + qn + ", name=" + name + "]";
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return qn.hashCode();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RBDomain) {
			RBDomain other = (RBDomain) obj;
			return this.qn.equals(other.qn);
		}
		return super.equals(obj);
	}

}
