/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import java.util.List;

import de.lichtflut.rb.core.content.ContentItem;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 * 	Definition of a widget.
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

	// -- The widget's position in it's port --------------

	Integer getPosition();

	void setPosition(Integer position);

    // -- selected content ----

    Selection getSelection();

    void setSelection(Selection selection);

    // -- referenced content -

    String getContentID();

    void setContentID(String contentID);
	
	// ----------------------------------------------------
	
	List<WidgetAction> getActions();

}
