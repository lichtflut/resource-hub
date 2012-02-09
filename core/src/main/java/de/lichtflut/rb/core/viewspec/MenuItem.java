/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  A menu item referreing to a perspective.
 * </p>
 *
 * <p>
 * 	Created Feb 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface MenuItem extends ResourceNode {
	
	ResourceID getID();
	
	String getName();
	
	void setName(String name);
	
	Integer getPosition();
	
	void setPosition(Integer position);

	Perspective getPerspective();
	
	void setPerspective(Perspective perspective);
	
}
