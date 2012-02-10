/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import java.util.List;

import org.arastreju.ogm.annotations.EntityNode;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

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
@EntityNode()
public interface Perspective extends ResourceNode {

	ResourceID getID();
	
	String getName();
	
	String getTitle();
	
	String getDescription();
	
	void setName(String name);
	
	void setTitle(String title);
	
	void setDescription(String desc);
	
	List<ViewPort> getViewPorts();
	
	ViewPort addViewPort();

}
