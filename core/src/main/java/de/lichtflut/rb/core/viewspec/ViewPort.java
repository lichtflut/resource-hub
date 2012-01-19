/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;

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
public interface ViewPort {
	
	ResourceID getID();
	
	int getColumn();
	
	int getOrder();
	
	Widget getWidget();

}
