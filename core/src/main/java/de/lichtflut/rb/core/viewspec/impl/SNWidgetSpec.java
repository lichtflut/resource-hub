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
import de.lichtflut.rb.core.viewspec.ColumnDef;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetAction;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.structure.OrderBySerialNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  Node representing a widget specification.
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNWidgetSpec extends ResourceView implements WidgetSpec {

    public static SNWidgetSpec from(SemanticNode node) {
        if (node instanceof SNWidgetSpec) {
            return (SNWidgetSpec) node;
        } else if (node instanceof ResourceNode) {
            return new SNWidgetSpec((ResourceNode) node);
        } else if (node instanceof ResourceID) {
            return new SNWidgetSpec(node.asResource());
        } else {
            return null;
        }
    }

    // ----------------------------------------------------

	/**
	 * Default constructor for new widget specifications.
	 */
	public SNWidgetSpec() {
	}
	
	/**
     * Constructor.
	 * @param resource The widget resource to be wrapped.
	 */
	public SNWidgetSpec(ResourceNode resource) {
		super(resource);
	}
	
	// ----------------------------------------------------

	@Override
	public ResourceID getID() {
		return this;
	}

	@Override
	public String getTitle() {
		return stringValue(RB.HAS_TITLE);
	}

    @Override
    public void setTitle(String title) {
        setValue(RB.HAS_TITLE, title);
    }

	@Override
	public String getDescription() {
		return stringValue(RB.HAS_DESCRIPTION);
	}

    @Override
    public void setDescription(String desc) {
        setValue(RB.HAS_DESCRIPTION, desc);
    }

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
	public Selection getSelection() {
		SemanticNode node = SNOPS.singleObject(this, WDGT.HAS_SELECTION);
		if (node != null && node.isResourceNode()) {
			return new SNSelection(node.asResource());
		} else {
			return null;
		}
	}

	@Override
	public void setSelection(Selection selection) {
		setValue(WDGT.HAS_SELECTION, selection);
	}

    // ----------------------------------------------------

    @Override
    public String getContentID() {
        return stringValue(WDGT.DISPLAYS_CONTENT_ITEM);
    }

    @Override
    public void setContentID(String contentID) {
        setValue(WDGT.DISPLAYS_CONTENT_ITEM, contentID);
    }

	// ----------------------------------------------------
	
	@Override
	public List<WidgetAction> getActions() {
		final List<WidgetAction> result = new ArrayList<WidgetAction>();
		for(Statement stmt : getAssociations(WDGT.SUPPORTS_ACTION)) {
			result.add(SNWidgetAction.from(stmt.getObject()));
		}
		return result;
	}

    @Override
    public void addAction(WidgetAction action) {
        addAssociation(WDGT.SUPPORTS_ACTION, action);
    }

    // ----------------------------------------------------

    @Override
    public List<ColumnDef> getColumns() {
        final List<ColumnDef> result = new ArrayList<ColumnDef>();
        for(Statement stmt : getAssociations(WDGT.DEFINES_COLUMN)) {
            result.add(SNColumnDef.from(stmt.getObject()));
        }
        Collections.sort(result, new OrderBySerialNumber());
        return result;
    }

    @Override
    public void addColumn(ColumnDef column) {
        int maxSerialNumber = 0;
        for (ColumnDef existing : getColumns()) {
            maxSerialNumber = Math.max(maxSerialNumber, existing.getPosition());
        }
        column.setPosition(maxSerialNumber +1);
        addAssociation(WDGT.DEFINES_COLUMN, column);
    }

    // ----------------------------------------------------
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
        sb.append("WidgetSpec[").append(getQualifiedName().getSimpleName()).append("]");
        ResourceID type = resourceValue(RDF.TYPE);
        if (type != null) {
            sb.append(";type=").append(type).append("\n");
        }
        if (getSelection() != null) {
		    sb.append("  Selection: ").append(getSelection()).append("\n");
        }
        for (WidgetAction action : getActions()) {
            sb.append("  Action: ").append(action).append("\n");
        }
        return sb.toString();
	}
	
}
