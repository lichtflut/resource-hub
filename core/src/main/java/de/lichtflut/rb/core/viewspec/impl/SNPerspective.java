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
package de.lichtflut.rb.core.viewspec.impl;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.common.Accessibility;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.structure.OrderBySerialNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.arastreju.sge.SNOPS.id;

/**
 * <p>
 *  A perspective.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPerspective extends ResourceView implements Perspective {

	/**
     * Create a view based on given resource.
	 * @param resource The resource.
	 */
	public SNPerspective(ResourceNode resource) {
		super(resource);
	}
	
	/**
     * Create an new perspective with given qualified name.
	 * @param qn The qualified name.
	 */
	public SNPerspective(QualifiedName qn) {
		super(qn);
		setValue(RDF.TYPE, WDGT.PERSPECTIVE);
	}

	/**
	 * Create a new perspective.
	 */
	public SNPerspective() {
		setValue(RDF.TYPE, WDGT.PERSPECTIVE);
	}
	
	// ----------------------------------------------------

	@Override
	public String getName() {
		return stringValue(RB.HAS_NAME);
	}

	@Override
	public String getTitle() {
		return stringValue(RB.HAS_TITLE);
	}

	@Override
	public String getDescription() {
		return stringValue(RB.HAS_DESCRIPTION);
	}

	@Override
	public void setName(String name) {
		setValue(RB.HAS_NAME, name);
	}

	@Override
	public void setTitle(String title) {
		setValue(RB.HAS_TITLE, title);
	}

	@Override
	public void setDescription(String desc) {
		setValue(RB.HAS_DESCRIPTION, desc);
	}

    // ----------------------------------------------------

    @Override
    public ResourceID getOwner() {
        SemanticNode owner = SNOPS.fetchObject(this, RB.HAS_OWNER);
        if (owner != null && owner.isResourceNode()) {
            return owner.asResource();
        }
        return null;
    }

    @Override
    public void setOwner(ResourceID owner) {
        setValue(RB.HAS_OWNER, owner);
    }

    @Override
    public Accessibility getVisibility() {
        SemanticNode accessibility = SNOPS.fetchObject(this, RB.HAS_READ_ACCESS);
        if (accessibility != null && accessibility.isResourceNode()) {
            final QualifiedName qn = accessibility.asResource().getQualifiedName();
            return Accessibility.getByQualifiedName(qn);
        }
        return Accessibility.PRIVATE;
    }

    @Override
    public void setVisibility(Accessibility visibility) {
        setValue(RB.HAS_READ_ACCESS, id(visibility.getQualifiedName()));
    }

    // ----------------------------------------------------

	@Override
	public List<ViewPort> getViewPorts() {
		final List<ViewPort> result = new ArrayList<ViewPort>();
		for(Statement stmt : getAssociations(WDGT.HAS_VIEW_PORT)) {
			result.add(new SNViewPort(stmt.getObject().asResource()));
		}
		Collections.sort(result, new OrderBySerialNumber());
		return result;
	}
	
	@Override
	public ViewPort addViewPort() {
		final SNViewPort port = new SNViewPort();
		port.setPosition(getViewPorts().size() +1);
		addAssociation(WDGT.HAS_VIEW_PORT, port);
		return port;
	}

}
