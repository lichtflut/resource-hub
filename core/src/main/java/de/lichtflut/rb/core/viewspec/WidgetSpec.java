/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

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
public interface WidgetSpec extends ResourceNode {

	ResourceID getID();
	
	String getTitle();
	
	void setTitle(String title);
	
	String getDescription();
	
	void setDescription(String desc);
	
	Selection getSelection();
	
	void setSelection(Selection selection);
	
	Integer getPosition();
	
	void setPosition(Integer position);
	
}
