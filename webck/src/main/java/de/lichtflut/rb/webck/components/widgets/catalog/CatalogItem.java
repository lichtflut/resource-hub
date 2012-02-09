/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets.catalog;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  Item of widget catalog.
 * </p>
 *
 * <p>
 * 	Created Feb 9, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class CatalogItem implements Serializable {
	
	private IModel<String> name;
	
	private IModel<String> description;
	
	// ----------------------------------------------------
	
	/**
	 * Default constructor.
	 */
	public CatalogItem() {
	}
	
	/**
	 * @param name
	 * @param description
	 */
	public CatalogItem(IModel<String> name, IModel<String> description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	// ----------------------------------------------------
	
	public abstract WidgetSpec createWidget();
	
	// ----------------------------------------------------
	
	/**
	 * @return the name
	 */
	public IModel<String> getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(IModel<String> name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public IModel<String> getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(IModel<String> description) {
		this.description = description;
	}
	
}
