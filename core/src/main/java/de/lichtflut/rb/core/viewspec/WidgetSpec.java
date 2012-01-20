/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import java.io.Serializable;

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
public interface WidgetSpec extends Serializable {

	ResourceID getID();
	
	String getName();
	
	String getTitle();
	
	String getDescription();
	
	Selection getSelection();
	
	void setName(String name);
	
	void setTitle(String title);
	
	void setDescription(String desc);
	
	void setSelection(Selection selection);
	
}
