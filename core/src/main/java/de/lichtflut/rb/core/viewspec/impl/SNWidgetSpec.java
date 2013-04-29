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

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.viewspec.Selection;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetAction;
import de.lichtflut.rb.core.viewspec.WidgetSpec;

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

	/**
	 * Default constructor for new widget specifications.
	 */
	public SNWidgetSpec() {
	}
	
	/**
	 * @param resource
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
	public String getDescription() {
		return stringValue(RB.HAS_DESCRIPTION);
	}

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
	public void setTitle(String title) {
		setValue(RB.HAS_TITLE, title);
	}

	@Override
	public void setDescription(String desc) {
		setValue(RB.HAS_DESCRIPTION, desc);
	}

	@Override
	public void setSelection(Selection selection) {
		setValue(WDGT.HAS_SELECTION, selection);
	}

    @Override
    public String getContentID() {
        return stringValue(WDGT.DISPLAYS_CONTENT_ITEM);
    }

    @Override
    public void setContentID(String contentID) {
        setValue(WDGT.DISPLAYS_CONTENT_ITEM, contentID);
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
	public List<WidgetAction> getActions() {
		final List<WidgetAction> result = new ArrayList<WidgetAction>();
		for(Statement stmt : getAssociations(WDGT.SUPPORTS_ACTION)) {
			result.add(new SNWidgetAction(stmt.getObject().asResource()));
		}
		return result;
	}
	
	// ----------------------------------------------------
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("WidgetSpec[" + getQualifiedName().getSimpleName() + "]\n");
        if (getSelection() != null) {
		    sb.append("  Selection: " + getSelection() + "\n");
        }
		return sb.toString();
	}
	
}
