/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import de.lichtflut.rb.core.common.Accessibility;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;

import java.util.List;

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
public interface Perspective extends ResourceNode {

    QualifiedName getQualifiedName();

    // ----------------------------------------------------
	
	String getName();
	
	String getTitle();
	
	String getDescription();
	
	void setName(String name);
	
	void setTitle(String title);
	
	void setDescription(String desc);

    // ----------------------------------------------------

    ResourceID getOwner();

    void setOwner(ResourceID owner);

    Accessibility getVisibility();

    void setVisibility(Accessibility visibility);

    // ----------------------------------------------------
	
	List<ViewPort> getViewPorts();
	
	ViewPort addViewPort();

}
