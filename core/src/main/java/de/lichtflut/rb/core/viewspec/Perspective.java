/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import static de.lichtflut.rb.core.viewspec.ViewSpec.*;

import java.io.Serializable;
import java.util.Collection;

import org.arastreju.ogm.annotations.EntityNode;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Definition of a dynamic view.
 * </p>
 *
 * <p>
 * 	Created Jan 19, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
@EntityNode(type=PERSPECTIVE_URI)
public interface Perspective extends Serializable {

	ResourceID getID();
	
	String getName();
	
	String getTitle();
	
	String getDescription();
	
	void setName(String name);
	
	void setTitle(String title);
	
	void setDescription(String desc);
	
	Collection<ViewPort> getViewPorts();

}
