/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import java.util.List;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Definition of a view port inside a perspective.
 * </p>
 *
 * <p>
 * 	Created Jan 19, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ViewPort extends ResourceNode {
	
	ResourceID getID();
	
	Integer getPosition();
	
	void setPosition(Integer position);
	
	List<WidgetSpec> getWidgets();
	
	void addWidget(WidgetSpec widget);
	
	void removeWidget(WidgetSpec widget);

}
