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

import static org.arastreju.sge.SNOPS.resource;
import static org.arastreju.sge.SNOPS.singleObject;

import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.WDGT;

/**
 * <p>
 *  Definition of a column.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNColumnDef extends ResourceView implements ColumnDef {

    public static SNColumnDef from(SemanticNode node) {
        if (node instanceof SNColumnDef) {
            return (SNColumnDef) node;
        } else if (node instanceof ResourceNode) {
            return new SNColumnDef((ResourceNode) node);
        } else if (node instanceof ResourceID) {
            return new SNColumnDef(node.asResource());
        } else {
            return null;
        }
    }

    // ----------------------------------------------------

	/**
	 * 
	 */
	public SNColumnDef() {
		super();
	}

	/**
	 * @param resource
	 */
	public SNColumnDef(ResourceNode resource) {
		super(resource);
	}
	
	// ----------------------------------------------------
	
	@Override
	public ResourceID getProperty() {
		return resource(singleObject(this, WDGT.CORRESPONDS_TO_PROPERTY));
	}

    @Override
    public void setProperty(ResourceID property) {
        setValue(WDGT.CORRESPONDS_TO_PROPERTY, property);
    }
	
	@Override
	public String getHeader() {
		return stringValue(WDGT.HAS_HEADER);
	}

    @Override
    public void setHeader(String header) {
        setValue(WDGT.HAS_HEADER, header);
    }

    // ----------------------------------------------------
	
	@Override
	public Integer getPosition() {
		return intValue(Aras.HAS_SERIAL_NUMBER);
	}
	
	@Override
	public void setPosition(Integer position) {
		setValue(Aras.HAS_SERIAL_NUMBER, position);
	}
	
	// ----------------------------------------------------
	
	@Override
	public String toString() {
		return getProperty() + " [" + getPosition() + "]";
	}
	
}
