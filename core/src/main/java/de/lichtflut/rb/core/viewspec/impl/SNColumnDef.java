/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import static org.arastreju.sge.SNOPS.resource;
import static org.arastreju.sge.SNOPS.singleObject;

import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.WDGT;

/**
 * <p>
 *  Definition of a column.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNColumnDef extends ResourceView implements ColumnDef {

	/**
	 * 
	 */
	public SNColumnDef() {
		super();
	}

	/**
	 * @param resource
	 */
	public SNColumnDef(ResourceNode resource) {
		super(resource);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getProperty() {
		return resource(singleObject(this, WDGT.CORRESPONDS_TO_PROPERTY));
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getHeader() {
		return stringValue(WDGT.HAS_HEADER);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Integer getPosition() {
		return intValue(Aras.HAS_SERIAL_NUMBER);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setProperty(ResourceID property) {
		setValue(WDGT.CORRESPONDS_TO_PROPERTY, property);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setHeader(String header) {
		setValue(WDGT.HAS_HEADER, header);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void setPosition(Integer position) {
		setValue(Aras.HAS_SERIAL_NUMBER, position);
	}
	
}
