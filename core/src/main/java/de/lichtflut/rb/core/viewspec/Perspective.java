/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
