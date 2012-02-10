/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.structure.OrderBySerialNumber;

import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  A view port of a perspective.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNViewPort extends ResourceView implements ViewPort {

	/**
	 * Create a view around an existing view port resource.
	 * @param resource The view port resource.
	 */
	public SNViewPort(ResourceNode resource) {
		super(resource);
	}

	/**
	 * Create a new view port.
	 */
	public SNViewPort() {
		setValue(RDF.TYPE, WDGT.VIEW_PORT);
	}
	
	// ----------------------------------------------------d

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public ResourceID getID() {
		return this;
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
	public void setPosition(Integer position) {
		setValue(Aras.HAS_SERIAL_NUMBER, position);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<WidgetSpec> getWidgets() {
		final List<WidgetSpec> result = new ArrayList<WidgetSpec>();
		for(Statement stmt : getAssociations(WDGT.CONTAINS_WIDGET)) {
			result.add(new SNWidgetSpec(stmt.getObject().asResource()));
		}
		Collections.sort(result, new OrderBySerialNumber());
		return result;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(WidgetSpec widget) {
		addAssociation(WDGT.CONTAINS_WIDGET, widget);
		widget.setPosition(getWidgets().size() + 1);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void removeWidget(WidgetSpec widget) {
		SNOPS.remove(this, WDGT.CONTAINS_WIDGET, widget);
	}
	
}
