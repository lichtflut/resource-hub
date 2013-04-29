/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
		widget.setPosition(getWidgets().size() + 1);
		addAssociation(WDGT.CONTAINS_WIDGET, widget);
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void removeWidget(WidgetSpec widget) {
		SNOPS.remove(this, WDGT.CONTAINS_WIDGET, widget);
	}
	
}
