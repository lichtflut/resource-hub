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
package de.lichtflut.rb.core.perceptions;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.RBSystem;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.InheritedDecorator;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Represents an item of a perception, may be a server, an application, a module, etc.
 * </p>
 *
 * <p>
 *  Created 29.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerceptionItem extends InheritedDecorator {

	public static PerceptionItem from(final SemanticNode node) {
		if (node instanceof PerceptionItem) {
			return (PerceptionItem) node;
		} else if (node instanceof ResourceNode) {
			return new PerceptionItem((ResourceNode) node);
		} else {
			return null;
		}
	}

	// ----------------------------------------------------

	public PerceptionItem() {
	}

	public PerceptionItem(final QualifiedName qn) {
		super(qn);
	}

	public PerceptionItem(final ResourceNode resource) {
		super(resource);
	}

	// ----------------------------------------------------

	public String getID() {
		return stringValue(RB.HAS_ID);
	}

	public void setID(final String id) {
		setValue(RB.HAS_ID, id);
	}

	public String getName() {
		return stringValue(RB.HAS_NAME);
	}

	public void setName(final String name) {
		setValue(RB.HAS_NAME, name);
	}

    public Perception getPerception() {
        return Perception.from(SNOPS.fetchObject(this, RBSystem.BELONGS_TO_PERCEPTION));
    }

    public void setPerception(Perception perception) {
        setValue(RBSystem.BELONGS_TO_PERCEPTION, perception);
    }

	public String getCanonicalName() {
		return "<no canonical name yet>";
	}

	// ----------------------------------------------------

    public void addSubItem(PerceptionItem item) {
        addAssociation(RB.HAS_CHILD_NODE, item);
    }

	public List<PerceptionItem> getSubItems() {
		final List<PerceptionItem> result = new ArrayList<PerceptionItem>();
		for (Statement assoc : directAssociations()) {
			SNProperty predicate = SNProperty.from(assoc.getPredicate());
			if (predicate.isSubPropertyOf(RB.HAS_CHILD_NODE) && assoc.getObject().isResourceNode()) {
				result.add(from(assoc.getObject()));
			}
		}
		return result;
	}

	// ----------------------------------------------------

	@Override
	public String toString() {
		return "PerceptionItem[" + getID() + "]";
	}


    public String printHierarchy(String delim) {
        StringBuilder sb = new StringBuilder(delim);
        sb.append(this);
        sb.append("\n");
        for (PerceptionItem sub : getSubItems()) {
            sb.append(delim);
            sb.append(sub.printHierarchy(delim + "  "));
        }
        return sb.toString();
    }
}
