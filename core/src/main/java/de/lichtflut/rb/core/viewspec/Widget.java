/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Definition of a widget.
 * </p>
 *
 * <p>
 * 	Created Jan 19, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Widget {

	ResourceID getID();
	
	String getName();
	
	String getTitle();
	
	String getDescription();
	
	void setName(String name);
	
	void setTitle(String title);
	
	void setDescription(String desc);
	
}
